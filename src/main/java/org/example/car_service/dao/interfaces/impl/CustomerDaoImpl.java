package org.example.car_service.dao.interfaces.impl;


import org.example.car_service.dao.interfaces.CustomerDao;
import org.example.car_service.model.Customer;

import java.sql.*;
import java.util.*;

public class CustomerDaoImpl implements CustomerDao {
    private final Connection conn;

    public CustomerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Customer c) {
        String sql = "INSERT INTO Customer(name, phone, email) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.name());
            ps.setString(2, c.phone());
            ps.setString(3, c.email());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Customer> findById(int id) {
        String sql = "SELECT id,name,phone,email FROM Customer WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT id,name,phone,email FROM Customer";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Customer> list = new ArrayList<>();
            while (rs.next()) list.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Customer c) {
        String sql = "UPDATE Customer SET name=?, phone=?, email=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.name());
            ps.setString(2, c.phone());
            ps.setString(3, c.email());
            ps.setInt(4, c.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Customer WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
