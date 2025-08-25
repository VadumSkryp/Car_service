package org.example.car_service.model;

import java.math.BigDecimal;

public class ServiceTask {
    private int id;
    private String description;
    private BigDecimal standardPrice;

    public ServiceTask() {
    }

    public ServiceTask(int id, String description, BigDecimal standardPrice) {
        this.id = id;
        this.description = description;
        this.standardPrice = standardPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    @Override
    public String toString() {
        return "ServiceTask{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", standardPrice=" + standardPrice +
                '}';
    }
}
