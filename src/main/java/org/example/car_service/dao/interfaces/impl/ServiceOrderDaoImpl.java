package org.example.car_service.dao.interfaces.impl;

import org.example.car_service.dao.interfaces.ServiceOrderDao;
import org.example.car_service.model.ServiceOrder;

import java.sql.*;
import java.util.*;

public class ServiceOrderDaoImpl implements ServiceOrderDao {
    private final Connection conn;

    public ServiceOrderDaoImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int create(ServiceOrder o) {
        String sql = "INSERT INTO ServiceOrder(car_id, mechanic_id, order_date, status) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, o.carId());
            ps.setInt(2, o.mechanicId());
            ps.setDate(3, o.orderDate());
            ps.setString(4, o.status());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<ServiceOrder> findById(int id) {
        String sql = "SELECT id, car_id, mechanic_id, order_date, status FROM ServiceOrder WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return Optional.of(new ServiceOrder(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getString(5)));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ServiceOrder> findAll() {
        String sql = "SELECT id, car_id, mechanic_id, order_date, status FROM ServiceOrder";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<ServiceOrder> list = new ArrayList<>();
            while (rs.next())
                list.add(new ServiceOrder(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getString(5)));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ServiceOrder> findByCarId(int carId) {
        String sql = "SELECT id, car_id, mechanic_id, order_date, status FROM ServiceOrder WHERE car_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, carId);
            try (ResultSet rs = ps.executeQuery()) {
                List<ServiceOrder> list = new ArrayList<>();
                while (rs.next())
                    list.add(new ServiceOrder(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getDate(4), rs.getString(5)));
                return list;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ServiceOrder o) {
        String sql = "UPDATE ServiceOrder SET car_id=?, mechanic_id=?, order_date=?, status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, o.carId());
            ps.setInt(2, o.mechanicId());
            ps.setDate(3, o.orderDate());
            ps.setString(4, o.status());
            ps.setInt(5, o.id());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM ServiceOrder WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
