package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.CustomerDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Customer;


import java.sql.*;
import java.util.*;

public class CustomerDaoImpl implements CustomerDao {

    private final Connection conn;

    // Constructor to inject database connection
    public CustomerDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Customer c) throws DaoException {
        String sql = "INSERT INTO Customer(name, phone, email) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.executeUpdate();

            // Retrieve generated key (ID)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            throw new DaoException("Error creating Customer", e);
        }
    }

    @Override
    public Optional<Customer> findById(int id) throws DaoException {
        String sql = "SELECT id, name, phone, email FROM Customer WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Customer(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4)
                    ));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("Error finding Customer by ID", e);
        }
    }

    @Override
    public List<Customer> findAll() throws DaoException {
        String sql = "SELECT id, name, phone, email FROM Customer";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Customer> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Error retrieving all Customers", e);
        }
    }

    @Override
    public void update(Customer c) throws DaoException {
        String sql = "UPDATE Customer SET name=?, phone=?, email=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getEmail());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error updating Customer", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM Customer WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error deleting Customer", e);
        }
    }
}
