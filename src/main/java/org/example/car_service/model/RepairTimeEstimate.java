package org.example.car_service.model;

public record RepairTimeEstimate(
        int serviceOrderId,
        double estimatedHours,
        double actualHours
) {}
