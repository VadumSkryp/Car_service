package org.example.car_service.model;

public class Car {
    private int id;
    private int customerId;
    private int carModelId;
    private String licensePlate;
    private int year;

    public Car(){}

    public Car(int id, int customerId, int carModelId, String licensePlate, int year) {
        this.id = id;
        this.customerId = customerId;
        this.carModelId = carModelId;
        this.licensePlate = licensePlate;
        this.year = year;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarModelId() {
        return carModelId;
    }

    public void setCarModelId(int carModelId) {
        this.carModelId = carModelId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", carModelId=" + carModelId +
                ", licensePlate='" + licensePlate + '\'' +
                ", year=" + year +
                '}';
    }
}
