package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.CarDao;
import org.example.car_service.model.Car;

import java.sql.*;
import java.util.*;

public class CarDaoImpl implements CarDao {
    private final Connection conn;

    public CarDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Car c) {
        String sql = "INSERT INTO Car(customer_id, car_model_id, license_plate, year) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.customerId());
            ps.setInt(2, c.carModelId());
            ps.setString(3, c.licensePlate());
            ps.setInt(4, c.year());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Car> findById(int id) {
        String sql = "SELECT id, customer_id, car_model_id, license_plate, year FROM Car WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(new Car(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> findAll() {
        String sql = "SELECT id, customer_id, car_model_id, license_plate, year FROM Car";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Car> list = new ArrayList<>();
            while (rs.next())
                list.add(new Car(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Car> findByCustomerId(int customerId) {
        String sql = "SELECT id, customer_id, car_model_id, license_plate, year FROM Car WHERE customer_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            List<Car> list = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    list.add(new Car(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5)));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Car c) {
        String sql = "UPDATE Car SET customer_id=?, car_model_id=?, license_plate=?, year=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.customerId());
            ps.setInt(2, c.carModelId());
            ps.setString(3, c.licensePlate());
            ps.setInt(4, c.year());
            ps.setInt(5, c.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM Car WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
