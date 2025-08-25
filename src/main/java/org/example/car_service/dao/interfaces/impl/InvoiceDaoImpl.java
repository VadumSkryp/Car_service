package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.InvoiceDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Invoice;


import java.sql.*;
import java.util.*;

public class InvoiceDaoImpl implements InvoiceDao {

    private final Connection conn;

    // Constructor to inject database connection
    public InvoiceDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Invoice inv) throws DaoException {
        String sql = "INSERT INTO Invoice(service_order_id, total_amount, invoice_date) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inv.getServiceOrderId());
            ps.setBigDecimal(2, inv.getTotalAmount());
            ps.setDate(3, inv.getInvoiceDate());
            ps.executeUpdate();

            // Retrieve generated key (ID)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            throw new DaoException("Error creating Invoice", e);
        }
    }

    @Override
    public Optional<Invoice> findById(int id) throws DaoException {
        String sql = "SELECT id, service_order_id, total_amount, invoice_date FROM Invoice WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Invoice(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getBigDecimal(3),
                            rs.getDate(4)
                    ));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DaoException("Error finding Invoice by ID", e);
        }
    }

    @Override
    public Optional<Invoice> findByServiceOrderId(int soId) throws DaoException {
        String sql = "SELECT id, service_order_id, total_amount, invoice_date FROM Invoice WHERE service_order_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Invoice(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getBigDecimal(3),
                            rs.getDate(4)
                    ));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DaoException("Error finding Invoice by Service Order ID", e);
        }
    }

    @Override
    public List<Invoice> findAll() throws DaoException {
        String sql = "SELECT id, service_order_id, total_amount, invoice_date FROM Invoice";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Invoice> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Invoice(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getBigDecimal(3),
                        rs.getDate(4)
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Error retrieving all Invoices", e);
        }
    }

    @Override
    public void update(Invoice inv) throws DaoException {
        String sql = "UPDATE Invoice SET service_order_id=?, total_amount=?, invoice_date=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inv.getServiceOrderId());
            ps.setBigDecimal(2, inv.getTotalAmount());
            ps.setDate(3, inv.getInvoiceDate());
            ps.setInt(4, inv.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error updating Invoice", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM Invoice WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error deleting Invoice", e);
        }
    }
}
