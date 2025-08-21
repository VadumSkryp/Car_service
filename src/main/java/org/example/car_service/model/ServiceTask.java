package org.example.car_service.model;

import java.math.BigDecimal;

public record ServiceTask(
        int id,
        String description,
        BigDecimal standardPrice
) {}
