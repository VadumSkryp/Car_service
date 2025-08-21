package org.example.car_service.model;

import java.math.BigDecimal;

public record Part(
        int id,
        String name,
        BigDecimal price,
        int stockQuantity
) {}
