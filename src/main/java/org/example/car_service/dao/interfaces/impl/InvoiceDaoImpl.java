package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.InvoiceDao;
import org.example.car_service.model.Invoice;

import java.sql.*;
import java.util.*;


public class InvoiceDaoImpl implements InvoiceDao {
    private final Connection conn;

    public InvoiceDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Invoice inv) {
        String sql = "INSERT INTO Invoice(service_order_id, total_amount, invoice_date) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inv.serviceOrderId());
            ps.setBigDecimal(2, inv.totalAmount());
            ps.setDate(3, inv.invoiceDate());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Invoice> findById(int id) {
        String sql = "SELECT id, service_order_id, total_amount, invoice_date FROM Invoice WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(new Invoice(rs.getInt(1), rs.getInt(2), rs.getBigDecimal(3), rs.getDate(4)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Invoice> findByServiceOrderId(int soId) {
        String sql = "SELECT id, service_order_id, total_amount, invoice_date FROM Invoice WHERE service_order_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(new Invoice(rs.getInt(1), rs.getInt(2), rs.getBigDecimal(3), rs.getDate(4)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Invoice> findAll() {
        String sql = "SELECT id, service_order_id, total_amount, invoice_date FROM Invoice";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Invoice> list = new ArrayList<>();
            while (rs.next())
                list.add(new Invoice(rs.getInt(1), rs.getInt(2), rs.getBigDecimal(3), rs.getDate(4)));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Invoice inv) {
        String sql = "UPDATE Invoice SET service_order_id=?, total_amount=?, invoice_date=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inv.serviceOrderId());
            ps.setBigDecimal(2, inv.totalAmount());
            ps.setDate(3, inv.invoiceDate());
            ps.setInt(4, inv.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Invoice WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
