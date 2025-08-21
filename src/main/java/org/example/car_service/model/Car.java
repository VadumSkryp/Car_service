package org.example.car_service.model;

public record Car(
        int id,
        int customerId,
        int carModelId,
        String licensePlate,
        int year
) {}
