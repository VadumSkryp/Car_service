package org.example.car_service.model;

import java.sql.Date;

public record CarOwnerDetails(
        int customerId,
        String address,
        Date birthDate
) {}
