package org.example.car_service.model;

import java.sql.Date;

public record ServiceOrder(
        int id,
        int carId,
        int mechanicId,
        Date orderDate,
        String status
) {}
