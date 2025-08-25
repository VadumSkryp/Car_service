package org.example.car_service.model;

import java.sql.Date;

public class ServiceOrder {
    private int id;
    private int carId;
    private int mechanicId;
    private Date orderDate;
    private String status;

    public ServiceOrder() {
    }

    public ServiceOrder(int id, int carId, int mechanicId, Date orderDate, String status) {
        this.id = id;
        this.carId = carId;
        this.mechanicId = mechanicId;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(int mechanicId) {
        this.mechanicId = mechanicId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceOrder{" +
                "id=" + id +
                ", carId=" + carId +
                ", mechanicId=" + mechanicId +
                ", orderDate=" + orderDate +
                ", status='" + status + '\'' +
                '}';
    }
}
