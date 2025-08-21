package org.example.car_service.model;

public record ServiceOrderPart(
        int serviceOrderId,
        int partId,
        int quantity
) {}
