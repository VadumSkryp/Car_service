package org.example.car_service.util;

import org.example.car_service.model.CarOwnerDetails;
import org.example.car_service.model.RepairTimeEstimate;
import java.util.List;

public class XmlService {
    private final CarOwnerDetailsSaxParser ownerParser = new CarOwnerDetailsSaxParser();
    private final RepairTimeEstimateSaxParser estimateParser = new RepairTimeEstimateSaxParser();

    public List<CarOwnerDetails> loadCarOwnerDetails(String path) {
        return ownerParser.parse(path);
    }

    public List<RepairTimeEstimate> loadRepairEstimates(String path) {
        return estimateParser.parse(path);
    }
}
