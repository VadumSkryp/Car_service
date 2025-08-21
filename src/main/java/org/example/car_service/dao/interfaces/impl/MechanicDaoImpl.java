package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.MechanicDao;
import org.example.car_service.model.Mechanic;

import java.sql.*;
import java.util.*;

public class MechanicDaoImpl implements MechanicDao {
    private final Connection conn;

    public MechanicDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Mechanic m) {
        String sql = "INSERT INTO Mechanic(name, phone) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.name());
            ps.setString(2, m.phone());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Mechanic> findById(int id) {
        String sql = "SELECT id, name, phone FROM Mechanic WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(new Mechanic(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Mechanic> findAll() {
        String sql = "SELECT id, name, phone FROM Mechanic";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Mechanic> list = new ArrayList<>();
            while (rs.next()) list.add(new Mechanic(rs.getInt(1), rs.getString(2), rs.getString(3)));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Mechanic m) {
        String sql = "UPDATE Mechanic SET name=?, phone=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.name());
            ps.setString(2, m.phone());
            ps.setInt(3, m.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Mechanic WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
