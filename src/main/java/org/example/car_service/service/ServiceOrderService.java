package org.example.car_service.service;


import org.example.car_service.dao.interfaces.InvoiceDao;
import org.example.car_service.dao.interfaces.PartDao;
import org.example.car_service.dao.interfaces.ServiceOrderDao;
import org.example.car_service.dao.interfaces.ServiceTaskDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Invoice;
import org.example.car_service.model.ServiceOrder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.sql.Date;

public class ServiceOrderService {
    private final ServiceOrderDao orderDao;
    private final ServiceTaskDao taskDao;
    private final PartDao partDao;
    private final InvoiceDao invoiceDao;
    private final Connection connection; // для вставки у таблиці-зв'язки

    public ServiceOrderService(ServiceOrderDao orderDao,
                               ServiceTaskDao taskDao,
                               PartDao partDao,
                               InvoiceDao invoiceDao,
                               Connection connection) {
        this.orderDao = orderDao;
        this.taskDao = taskDao;
        this.partDao = partDao;
        this.invoiceDao = invoiceDao;
        this.connection = connection;
    }

    public int openOrder(int carId, int mechanicId, Date orderDate) throws DaoException {
        return orderDao.create(new ServiceOrder(0, carId, mechanicId, orderDate, "In Progress"));
    }

    public void addTasks(int serviceOrderId, Map<Integer, Integer> taskIdToQty) {
        String sql = "INSERT INTO ServiceOrder_Task(service_order_id, service_task_id, quantity) VALUES(?,?,?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (var e : taskIdToQty.entrySet()) {
                ps.setInt(1, serviceOrderId);
                ps.setInt(2, e.getKey());
                ps.setInt(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addParts(int serviceOrderId, Map<Integer, Integer> partIdToQty) {
        String sql = "INSERT INTO ServiceOrder_Part(service_order_id, part_id, quantity) VALUES(?,?,?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + VALUES(quantity)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (var e : partIdToQty.entrySet()) {
                ps.setInt(1, serviceOrderId);
                ps.setInt(2, e.getKey());
                ps.setInt(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal calculateTotal(int serviceOrderId) {
        BigDecimal tasks = BigDecimal.ZERO;
        BigDecimal parts = BigDecimal.ZERO;

        // сумуємо ServiceTask
        String q1 = """
                    SELECT t.standard_price, sot.quantity
                    FROM ServiceOrder_Task sot
                    JOIN ServiceTask t ON t.id = sot.service_task_id
                    WHERE sot.service_order_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(q1)) {
            ps.setInt(1, serviceOrderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal price = rs.getBigDecimal(1);
                    int qty = rs.getInt(2);
                    tasks = tasks.add(price.multiply(BigDecimal.valueOf(qty)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // сумуємо Parts
        String q2 = """
                    SELECT p.price, sop.quantity
                    FROM ServiceOrder_Part sop
                    JOIN Part p ON p.id = sop.part_id
                    WHERE sop.service_order_id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(q2)) {
            ps.setInt(1, serviceOrderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal price = rs.getBigDecimal(1);
                    int qty = rs.getInt(2);
                    parts = parts.add(price.multiply(BigDecimal.valueOf(qty)));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tasks.add(parts);
    }

    public int generateInvoice(int serviceOrderId, Date invoiceDate) throws DaoException {
        BigDecimal total = calculateTotal(serviceOrderId);
        return invoiceDao.create(new Invoice(0, serviceOrderId, total, invoiceDate));
    }

    public void updateStatus(int serviceOrderId, String status) throws DaoException {
        ServiceOrder existing = orderDao.findById(serviceOrderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        orderDao.update(new ServiceOrder(existing.getId(), existing.getCarId(), existing.getMechanicId(),
                existing.getOrderDate(), status));
    }
}
