package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.ServiceTaskDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.ServiceTask;


import java.sql.*;
import java.util.*;

public class ServiceTaskDaoImpl implements ServiceTaskDao {

    private final Connection conn;

    // Constructor to inject the database connection
    public ServiceTaskDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(ServiceTask t) throws DaoException {
        String sql = "INSERT INTO ServiceTask(description, standard_price) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, t.getDescription());
            ps.setBigDecimal(2, t.getStandardPrice());
            ps.executeUpdate();

            // Retrieve generated key (ID)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            throw new DaoException("Error creating ServiceTask", e);
        }
    }

    @Override
    public Optional<ServiceTask> findById(int id) throws DaoException {
        String sql = "SELECT id, description, standard_price FROM ServiceTask WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new ServiceTask(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getBigDecimal(3)
                    ));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DaoException("Error finding ServiceTask by ID", e);
        }
    }

    @Override
    public List<ServiceTask> findAll() throws DaoException {
        String sql = "SELECT id, description, standard_price FROM ServiceTask";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<ServiceTask> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new ServiceTask(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getBigDecimal(3)
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Error retrieving all ServiceTasks", e);
        }
    }

    @Override
    public void update(ServiceTask t) throws DaoException {
        String sql = "UPDATE ServiceTask SET description=?, standard_price=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getDescription());
            ps.setBigDecimal(2, t.getStandardPrice());
            ps.setInt(3, t.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error updating ServiceTask", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM ServiceTask WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error deleting ServiceTask", e);
        }
    }
}
