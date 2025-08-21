package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.PartDao;
import org.example.car_service.model.Part;

import java.sql.*;
import java.util.*;


public class PartDaoImpl implements PartDao {
    private final Connection conn;

    public PartDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Part p) {
        String sql = "INSERT INTO Part(name, price, stock_quantity) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.name());
            ps.setBigDecimal(2, p.price());
            ps.setInt(3, p.stockQuantity());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Part> findById(int id) {
        String sql = "SELECT id, name, price, stock_quantity FROM Part WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(new Part(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Part> findAll() {
        String sql = "SELECT id, name, price, stock_quantity FROM Part";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Part> list = new ArrayList<>();
            while (rs.next()) list.add(new Part(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4)));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Part p) {
        String sql = "UPDATE Part SET name=?, price=?, stock_quantity=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.name());
            ps.setBigDecimal(2, p.price());
            ps.setInt(3, p.stockQuantity());
            ps.setInt(4, p.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Part WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
