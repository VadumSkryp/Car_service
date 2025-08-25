package org.example.car_service.model;

public class ServiceOrderTask {
    private int serviceOrderId;
    private int serviceTaskId;
    private int quantity;

    public ServiceOrderTask() {
    }

    public ServiceOrderTask(int serviceOrderId, int serviceTaskId, int quantity) {
        this.serviceOrderId = serviceOrderId;
        this.serviceTaskId = serviceTaskId;
        this.quantity = quantity;
    }

    public int getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(int serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public int getServiceTaskId() {
        return serviceTaskId;
    }

    public void setServiceTaskId(int serviceTaskId) {
        this.serviceTaskId = serviceTaskId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ServiceOrderTask{" +
                "serviceOrderId=" + serviceOrderId +
                ", serviceTaskId=" + serviceTaskId +
                ", quantity=" + quantity +
                '}';
    }
}
