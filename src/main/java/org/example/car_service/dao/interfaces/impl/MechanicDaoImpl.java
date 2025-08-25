package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.MechanicDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Mechanic;


import java.sql.*;
import java.util.*;

public class MechanicDaoImpl implements MechanicDao {

    private final Connection conn;

    // Constructor to inject database connection
    public MechanicDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(Mechanic m) throws DaoException {
        String sql = "INSERT INTO Mechanic(name, phone) VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getPhone());
            ps.executeUpdate();

            // Retrieve generated key (ID)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            throw new DaoException("Error creating Mechanic", e);
        }
    }

    @Override
    public Optional<Mechanic> findById(int id) throws DaoException {
        String sql = "SELECT id, name, phone FROM Mechanic WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Mechanic(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)
                    ));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DaoException("Error finding Mechanic by ID", e);
        }
    }

    @Override
    public List<Mechanic> findAll() throws DaoException {
        String sql = "SELECT id, name, phone FROM Mechanic";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Mechanic> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Mechanic(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Error retrieving all Mechanics", e);
        }
    }

    @Override
    public void update(Mechanic m) throws DaoException {
        String sql = "UPDATE Mechanic SET name=?, phone=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getName());
            ps.setString(2, m.getPhone());
            ps.setInt(3, m.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error updating Mechanic", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM Mechanic WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error deleting Mechanic", e);
        }
    }
}
