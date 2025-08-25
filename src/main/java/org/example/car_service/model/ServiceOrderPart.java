package org.example.car_service.model;

public class ServiceOrderPart {
    private int serviceOrderId;
    private int partId;
    private int quantity;

    public ServiceOrderPart() {
    }

    public ServiceOrderPart(int serviceOrderId, int partId, int quantity) {
        this.serviceOrderId = serviceOrderId;
        this.partId = partId;
        this.quantity = quantity;
    }

    public int getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(int serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public int getPartId() {
        return partId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ServiceOrderPart{" +
                "serviceOrderId=" + serviceOrderId +
                ", partId=" + partId +
                ", quantity=" + quantity +
                '}';
    }
}
