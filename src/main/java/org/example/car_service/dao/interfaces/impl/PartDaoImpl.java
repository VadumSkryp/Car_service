package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.PartDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Part;


import java.sql.*;
import java.util.*;

public class PartDaoImpl implements PartDao {

    private final Connection conn;

    // Constructor to inject database connection
    public PartDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Part p) throws DaoException {
        String sql = "INSERT INTO Part(name, price, stock_quantity) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getStockQuantity());
            ps.executeUpdate();

            // Retrieve generated key (ID)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            throw new DaoException("Error creating Part", e);
        }
    }

    @Override
    public Optional<Part> findById(int id) throws DaoException {
        String sql = "SELECT id, name, price, stock_quantity FROM Part WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Part(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getBigDecimal(3),
                            rs.getInt(4)
                    ));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DaoException("Error finding Part by ID", e);
        }
    }

    @Override
    public List<Part> findAll() throws DaoException {
        String sql = "SELECT id, name, price, stock_quantity FROM Part";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Part> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Part(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getBigDecimal(3),
                        rs.getInt(4)
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Error retrieving all Parts", e);
        }
    }

    @Override
    public void update(Part p) throws DaoException {
        String sql = "UPDATE Part SET name=?, price=?, stock_quantity=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setBigDecimal(2, p.getPrice());
            ps.setInt(3, p.getStockQuantity());
            ps.setInt(4, p.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error updating Part", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM Part WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error deleting Part", e);
        }
    }
}
