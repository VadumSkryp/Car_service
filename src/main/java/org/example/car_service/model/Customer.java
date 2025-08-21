package org.example.car_service.model;

public record Customer(
        int id,
        String name,
        String phone,
        String email
) {}
