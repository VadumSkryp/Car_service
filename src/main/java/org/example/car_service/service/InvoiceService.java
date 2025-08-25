package org.example.car_service.service;

import org.example.car_service.dao.interfaces.InvoiceDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Invoice;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;

public class InvoiceService {
    private final InvoiceDao invoiceDao;
    private final Connection connection;

    public InvoiceService(InvoiceDao invoiceDao, Connection connection) {
        this.invoiceDao = invoiceDao;
        this.connection = connection;
    }

    public Optional<Invoice> getByOrder(int serviceOrderId) throws DaoException {
        return invoiceDao.findByServiceOrderId(serviceOrderId);
    }

    public void payInvoice(int invoiceId, Date paymentDate, BigDecimal amount) {
        String sql = "INSERT INTO Payment(invoice_id, payment_date, amount) VALUES(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, invoiceId);
            ps.setDate(2, paymentDate);
            ps.setBigDecimal(3, amount);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
