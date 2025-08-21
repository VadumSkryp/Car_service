package org.example.car_service.model;

public record ServiceOrderTask(
        int serviceOrderId,
        int serviceTaskId,
        int quantity
) {}