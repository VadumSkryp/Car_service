package org.example.car_service.model;

public class RepairTimeEstimate {
    private int serviceOrderId;
    private double estimatedHours;
    private double actualHours;

    public RepairTimeEstimate() {
    }

    public RepairTimeEstimate(int serviceOrderId, double estimatedHours, double actualHours) {
        this.serviceOrderId = serviceOrderId;
        this.estimatedHours = estimatedHours;
        this.actualHours = actualHours;
    }

    public int getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(int serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public double getActualHours() {
        return actualHours;
    }

    public void setActualHours(double actualHours) {
        this.actualHours = actualHours;
    }

    @Override
    public String toString() {
        return "RepairTimeEstimate{" +
                "serviceOrderId=" + serviceOrderId +
                ", estimatedHours=" + estimatedHours +
                ", actualHours=" + actualHours +
                '}';
    }
}
