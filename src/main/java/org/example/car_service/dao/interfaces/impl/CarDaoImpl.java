package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.CarDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarDaoImpl implements CarDao {
    private final Connection conn;

    public CarDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Car c) throws DaoException {

        if (findByLicensePlate(c.getLicensePlate()).isPresent()) {
            throw new DaoException("Car with license plate " + c.getLicensePlate() + " already exists.");
        }

        String sql = "INSERT INTO Car(customer_id, car_model_id, license_plate, year) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getCustomerId());
            ps.setInt(2, c.getCarModelId());
            ps.setString(3, c.getLicensePlate());
            ps.setInt(4, c.getYear());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to create car", e);
        }
    }

    @Override
    public Optional<Car> findById(int id) throws DaoException {
        String sql = "SELECT id, customer_id, car_model_id, license_plate, year FROM Car WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(new Car(rs.getInt("id"), rs.getInt("customer_id"),
                            rs.getInt("car_model_id"), rs.getString("license_plate"), rs.getInt("year")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("Failed to find car by id", e);
        }
    }

    @Override
    public List<Car> findAll() throws DaoException {
        String sql = "SELECT id, customer_id, car_model_id, license_plate, year FROM Car";
        List<Car> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Car(rs.getInt("id"), rs.getInt("customer_id"),
                        rs.getInt("car_model_id"), rs.getString("license_plate"), rs.getInt("year")));
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException("Failed to find all cars", e);
        }
    }

    @Override
    public List<Car> findByCustomerId(int customerId) throws DaoException {
        String sql = "SELECT id, customer_id, car_model_id, license_plate, year FROM Car WHERE customer_id=?";
        List<Car> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Car(rs.getInt("id"), rs.getInt("customer_id"),
                            rs.getInt("car_model_id"), rs.getString("license_plate"), rs.getInt("year")));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new DaoException("Failed to find cars by customer id", e);
        }
    }

    @Override
    public void update(Car c) throws DaoException {
        String sql = "UPDATE Car SET customer_id=?, car_model_id=?, license_plate=?, year=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getCustomerId());
            ps.setInt(2, c.getCarModelId());
            ps.setString(3, c.getLicensePlate());
            ps.setInt(4, c.getYear());
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to update car", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM Car WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Failed to delete car", e);
        }
    }

    @Override
    public Optional<Car> findByLicensePlate(String plate) throws DaoException {
        String sql = "SELECT id, customer_id, car_model_id, license_plate, year FROM Car WHERE license_plate=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Car(rs.getInt("id"), rs.getInt("customer_id"),
                            rs.getInt("car_model_id"), rs.getString("license_plate"), rs.getInt("year")));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("Failed to find car by license plate", e);
        }
    }
}
