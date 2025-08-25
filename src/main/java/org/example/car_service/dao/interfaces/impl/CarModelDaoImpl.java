package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.CarModelDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.CarModel;


import java.sql.*;
import java.util.*;

public class CarModelDaoImpl implements CarModelDao {

    private final Connection conn;

    // Constructor to inject the database connection
    public CarModelDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(CarModel m) throws DaoException {
        String sql = "INSERT INTO CarModel(manufacturer, model_name) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getManufacturer());
            ps.setString(2, m.getModelName());
            ps.executeUpdate();

            // Retrieve generated key (ID)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new DaoException("Error creating CarModel", e);
        }
    }

    @Override
    public Optional<CarModel> findById(int id) throws DaoException {
        String sql = "SELECT id, manufacturer, model_name FROM CarModel WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new CarModel(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DaoException("Error finding CarModel by ID", e);
        }
    }

    @Override
    public List<CarModel> findAll() throws DaoException {
        String sql = "SELECT id, manufacturer, model_name FROM CarModel";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<CarModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new CarModel(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Error retrieving all CarModels", e);
        }
    }

    @Override
    public void update(CarModel m) throws DaoException {
        String sql = "UPDATE CarModel SET manufacturer=?, model_name=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getManufacturer());
            ps.setString(2, m.getModelName());
            ps.setInt(3, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error updating CarModel", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM CarModel WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Error deleting CarModel", e);
        }
    }
}
