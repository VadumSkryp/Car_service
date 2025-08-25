
package org.example.car_service.util;


import org.example.car_service.model.CarOwnerDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CarOwnerDetailsSaxParser {

    private static final Logger logger = LoggerFactory.getLogger(CarOwnerDetailsSaxParser.class);

    public List<CarOwnerDetails> parse(String filePath) {
        List<CarOwnerDetails> list = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                CarOwnerDetails details;
                String currentElement = "";
                int customerId;
                String address;
                Date birthDate;

                StringBuilder data = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    currentElement = qName;
                    data.setLength(0);
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    data.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    switch (qName) {
                        case "customerId" -> customerId = Integer.parseInt(data.toString());
                        case "address" -> address = data.toString();
                        case "birthDate" -> birthDate = Date.valueOf(data.toString());
                        case "carOwnerDetails" -> {
                            details = new CarOwnerDetails(customerId, address, birthDate);
                            list.add(details);
                        }
                    }
                }
            };

            saxParser.parse(new File(filePath), handler);

        } catch (Exception e) {
            logger.error(" Error: {}", e.getMessage());
        }
        return list;
    }
}
