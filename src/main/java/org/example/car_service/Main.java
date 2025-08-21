package org.example.car_service;

import org.example.car_service.conection.ConnectionPool;
import org.example.car_service.dao.interfaces.*;
import org.example.car_service.dao.interfaces.impl.*;
import org.example.car_service.model.CarModel;
import org.example.car_service.model.Invoice;
import org.example.car_service.model.Part;
import org.example.car_service.model.Mechanic;
import org.example.car_service.model.ServiceTask;
import org.example.car_service.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try (Connection conn = ConnectionPool.getConnection()) {

            // Initialize DAO objects
            CustomerDao customerDao = new CustomerDaoImpl(conn);
            CarModelDao carModelDao = new CarModelDaoImpl(conn);
            CarDao carDao = new CarDaoImpl(conn);
            MechanicDao mechanicDao = new MechanicDaoImpl(conn);
            ServiceTaskDao taskDao = new ServiceTaskDaoImpl(conn);
            PartDao partDao = new PartDaoImpl(conn);
            ServiceOrderDao orderDao = new ServiceOrderDaoImpl(conn);
            InvoiceDao invoiceDao = new InvoiceDaoImpl(conn);

            // Initialize service layer
            CustomerService customerService = new CustomerService(customerDao, carDao);
            CarService carService = new CarService(carDao, carModelDao);
            MechanicService mechanicService = new MechanicService(mechanicDao);
            ServiceOrderService orderService = new ServiceOrderService(orderDao, taskDao, partDao, invoiceDao, conn);
            InvoiceService invoiceService = new InvoiceService(invoiceDao, conn);

            // 1) Register a new customer
            int custId = customerService.registerCustomer("Alice Cooper", "15550001111", "alice@example.com");
            logger.info("New customer id = {}", custId);

            // 2) Get or create a car model (Toyota Corolla)
            int modelId = carModelDao.findAll().stream().findFirst()
                    .map(CarModel::id)
                    .orElseGet(() -> carModelDao.create(new CarModel(0, "Toyota", "Corolla")));

            // 3) Add a car for the customer
            int carId = carService.addCarForCustomer(custId, modelId, "KY1234AB", 2020);
            logger.info("New car id = {}", carId);

            // 4) Get or create a mechanic
            int mechId = mechanicService.listMechanics().stream().findFirst()
                    .map(Mechanic::id)
                    .orElseGet(() -> mechanicService.addMechanic("Robert Wilson", "15554321111"));

            // 5) Open a new service order for the car
            int orderId = orderService.openOrder(carId, mechId, Date.valueOf(LocalDate.now()));
            logger.info("Opened order id = {}", orderId);

            // 6) Ensure service tasks and parts exist (create them if missing)
            int oilChangeId = taskDao.findAll().stream()
                    .filter(t -> t.description().equalsIgnoreCase("Oil change"))
                    .map(ServiceTask::id).findFirst()
                    .orElseGet(() -> taskDao.create(new ServiceTask(0, "Oil change", new BigDecimal("100.00"))));

            int brakeReplacementId = taskDao.findAll().stream()
                    .filter(t -> t.description().equalsIgnoreCase("Brake replacement"))
                    .map(ServiceTask::id).findFirst()
                    .orElseGet(() -> taskDao.create(new ServiceTask(0, "Brake replacement", new BigDecimal("250.00"))));

            int oilFilterId = partDao.findAll().stream()
                    .filter(p -> p.name().equalsIgnoreCase("Oil filter"))
                    .map(Part::id).findFirst()
                    .orElseGet(() -> partDao.create(new Part(0, "Oil filter", new BigDecimal("30.00"), 10)));

            int oil5w30Id = partDao.findAll().stream()
                    .filter(p -> p.name().equalsIgnoreCase("Motor oil 5W-30"))
                    .map(Part::id).findFirst()
                    .orElseGet(() -> partDao.create(new Part(0, "Motor oil 5W-30", new BigDecimal("40.00"), 20)));

            // 7) Add tasks and parts to the service order
            orderService.addTasks(orderId, Map.of(oilChangeId, 1, brakeReplacementId, 1));
            orderService.addParts(orderId, Map.of(oilFilterId, 1, oil5w30Id, 4));

            // 8) Generate an invoice for the order
            int invoiceId = orderService.generateInvoice(orderId, Date.valueOf(LocalDate.now()));
            logger.info("Generated invoice id = {}", invoiceId);

            // 9) Update service order status to "Completed"
            orderService.updateStatus(orderId, "Completed");
            logger.info("Order {} marked as Completed", orderId);

            // 10) Pay the invoice
            var invoiceOpt = invoiceService.getByOrder(orderId);
            if (invoiceOpt.isPresent()) {
                Invoice inv = invoiceOpt.get();
                invoiceService.payInvoice(inv.id(), Date.valueOf(LocalDate.now()), inv.totalAmount());
                logger.info("Invoice paid: {}", inv);
            } else {
                logger.warn("Invoice not found for order {}", orderId);
            }

        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
        }
    }
}
