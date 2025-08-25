
package org.example.car_service.util;

import org.example.car_service.model.RepairTimeEstimate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;

import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepairTimeEstimateSaxParser {

    private static final Logger logger = LoggerFactory.getLogger(RepairTimeEstimateSaxParser.class);

    public List<RepairTimeEstimate> parse(String filePath) {
        List<RepairTimeEstimate> list = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                RepairTimeEstimate estimate;
                String currentElement = "";
                int serviceOrderId;
                double estimatedHours;
                double actualHours;

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
                        case "serviceOrderId" -> serviceOrderId = Integer.parseInt(data.toString());
                        case "estimatedHours" -> estimatedHours = Double.parseDouble(data.toString());
                        case "actualHours" -> actualHours = Double.parseDouble(data.toString());
                        case "repairTimeEstimate" -> {
                            estimate = new RepairTimeEstimate(serviceOrderId, estimatedHours, actualHours);
                            list.add(estimate);
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
