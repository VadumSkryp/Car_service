package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.CarModelDao;
import org.example.car_service.model.CarModel;

import java.sql.*;
import java.util.*;

public class CarModelDaoImpl implements CarModelDao {
    private final Connection conn;

    public CarModelDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(CarModel m) {
        String sql = "INSERT INTO CarModel(manufacturer, model_name) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.manufacturer());
            ps.setString(2, m.modelName());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<CarModel> findById(int id) {
        String sql = "SELECT id, manufacturer, model_name FROM CarModel WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(new CarModel(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CarModel> findAll() {
        String sql = "SELECT id, manufacturer, model_name FROM CarModel";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<CarModel> list = new ArrayList<>();
            while (rs.next()) list.add(new CarModel(rs.getInt(1), rs.getString(2), rs.getString(3)));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(CarModel m) {
        String sql = "UPDATE CarModel SET manufacturer=?, model_name=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.manufacturer());
            ps.setString(2, m.modelName());
            ps.setInt(3, m.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM CarModel WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
