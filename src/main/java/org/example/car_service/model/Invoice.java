package org.example.car_service.model;

import java.math.BigDecimal;
import java.sql.Date;

public record Invoice(
        int id,
        int serviceOrderId,
        BigDecimal totalAmount,
        Date invoiceDate
) {}
