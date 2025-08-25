package org.example.car_service.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Invoice {
    private int id;
    private int serviceOrderId;
    private BigDecimal totalAmount;
    private Date invoiceDate;

    public Invoice() {}

    public Invoice(int id, int serviceOrderId, BigDecimal totalAmount, Date invoiceDate) {
        this.id = id;
        this.serviceOrderId = serviceOrderId;
        this.totalAmount = totalAmount;
        this.invoiceDate = invoiceDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServiceOrderId() {
        return serviceOrderId;
    }

    public void setServiceOrderId(int serviceOrderId) {
        this.serviceOrderId = serviceOrderId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", serviceOrderId=" + serviceOrderId +
                ", totalAmount=" + totalAmount +
                ", invoiceDate=" + invoiceDate +
                '}';
    }
}
