package org.example.car_service.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Payment {
    private int id;
    private int invoiceId;
    private Date paymentDate;
    private BigDecimal amount;

    public Payment() {
    }

    public Payment(int id, int invoiceId, Date paymentDate, BigDecimal amount) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", invoiceId=" + invoiceId +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }
}
