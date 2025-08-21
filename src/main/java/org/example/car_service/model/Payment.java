package org.example.car_service.model;

import java.math.BigDecimal;
import java.sql.Date;

public record Payment(
        int id,
        int invoiceId,
        Date paymentDate,
        BigDecimal amount
) {}
