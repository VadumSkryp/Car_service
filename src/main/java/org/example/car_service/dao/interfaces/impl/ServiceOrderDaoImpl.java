package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.ServiceOrderDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.ServiceOrder;


import java.sql.*;
import java.util.*;

public class ServiceOrderDaoImpl implements ServiceOrderDao {

    private final Connection conn;

    // Constructor to inject the database connection
    public ServiceOrderDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(ServiceOrder o) throws DaoException {
        String sql = "INSERT INTO ServiceOrder(car_id, mechanic_id, order_date, status) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, o.getCarId());
            ps.setInt(2, o.getMechanicId());
            ps.setDate(3, o.getOrderDate());
            ps.setString(4, o.getStatus());
            ps.executeUpdate();

            // Retrieve generated key (ID)
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }

        } catch (SQLException e) {
            throw new DaoException("Error creating ServiceOrder", e);
        }
    }

    @Override
    public Optional<ServiceOrder> findById(int id) throws DaoException {
        String sql = "SELECT id, car_id, mechanic_id, order_date, status FROM ServiceOrder WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new ServiceOrder(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getDate(4),
                            rs.getString(5)
                    ));
                }
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new DaoException("Error finding ServiceOrder by ID", e);
        }
    }

    @Override
    public List<ServiceOrder> findAll() throws DaoException {
        String sql = "SELECT id, car_id, mechanic_id, order_date, status FROM ServiceOrder";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<ServiceOrder> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new ServiceOrder(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getString(5)
                ));
            }
            return list;

        } catch (SQLException e) {
            throw new DaoException("Error retrieving all ServiceOrders", e);
        }
    }

    @Override
    public List<ServiceOrder> findByCarId(int carId) throws DaoException {
        String sql = "SELECT id, car_id, mechanic_id, order_date, status FROM ServiceOrder WHERE car_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            try (ResultSet rs = ps.executeQuery()) {

                List<ServiceOrder> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new ServiceOrder(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getDate(4),
                            rs.getString(5)
                    ));
                }
                return list;

            }
        } catch (SQLException e) {
            throw new DaoException("Error finding ServiceOrders by Car ID", e);
        }
    }

    @Override
    public void update(ServiceOrder o) throws DaoException {
        String sql = "UPDATE ServiceOrder SET car_id=?, mechanic_id=?, order_date=?, status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.getCarId());
            ps.setInt(2, o.getMechanicId());
            ps.setDate(3, o.getOrderDate());
            ps.setString(4, o.getStatus());
            ps.setInt(5, o.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error updating ServiceOrder", e);
        }
    }

    @Override
    public void delete(int id) throws DaoException {
        String sql = "DELETE FROM ServiceOrder WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Error deleting ServiceOrder", e);
        }
    }
}
