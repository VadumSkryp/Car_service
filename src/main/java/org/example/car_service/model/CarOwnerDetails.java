package org.example.car_service.model;

import java.sql.Date;

public class CarOwnerDetails {
    private int customerId;
    private String address;
    private Date birthDate;


    public CarOwnerDetails(){}

    // Конструктор
    public CarOwnerDetails(int customerId, String address, Date birthDate) {
        this.customerId = customerId;
        this.address = address;
        this.birthDate = birthDate;
    }

    // Геттери і сеттери
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    // Для зручного виводу
    @Override
    public String toString() {
        return "CarOwnerDetails{" +
                "customerId=" + customerId +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
