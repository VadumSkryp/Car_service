package org.example.car_service;

import org.example.car_service.util.ConnectionPool;
import org.example.car_service.dao.interfaces.*;
import org.example.car_service.dao.interfaces.impl.*;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.CarModel;
import org.example.car_service.model.Invoice;
import org.example.car_service.model.Part;
import org.example.car_service.model.Mechanic;
import org.example.car_service.model.ServiceTask;
import org.example.car_service.service.*;
import org.example.car_service.util.XmlService;
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
                    .map(CarModel::getId)
                    .orElseGet(() -> {
                        try {
                            return carModelDao.create(new CarModel(0, "Toyota", "Corolla"));
                        } catch (DaoException e) {
                            throw new RuntimeException(e);
                        }
                    });

            // 3) Add a car for the customer
            int carId = carService.addCarForCustomer(custId, modelId, "KY1234AB", 2020);
            logger.info("New car id = {}", carId);

            // 4) Get or create a mechanic
            int mechId = mechanicService.listMechanics().stream().findFirst()
                    .map(Mechanic::getId)
                    .orElseGet(() -> {
                        try {
                            return mechanicService.addMechanic("Robert Wilson", "15554321111");
                        } catch (DaoException e) {
                            logger.error("Error", e);
                        }
                        return 0;
                    });

            // 5) Open a new service order for the car
            int orderId = orderService.openOrder(carId, mechId, Date.valueOf(LocalDate.now()));
            logger.info("Opened order id = {}", orderId);

            // 6) Ensure service tasks and parts exist (create them if missing)
            int oilChangeId = taskDao.findAll().stream()
                    .filter(t -> t.getDescription().equalsIgnoreCase("Oil change"))
                    .map(ServiceTask::getId).findFirst()
                    .orElseGet(() -> {
                        try {
                            return taskDao.create(new ServiceTask(0, "Oil change", new BigDecimal("100.00")));
                        } catch (DaoException e) {
                            logger.error("Error", e);

                        }
                        return 0;
                    });

            int brakeReplacementId = taskDao.findAll().stream()
                    .filter(t -> t.getDescription().equalsIgnoreCase("Brake replacement"))
                    .map(ServiceTask::getId).findFirst()
                    .orElseGet(() -> {
                        try {
                            return taskDao.create(new ServiceTask(0, "Brake replacement", new BigDecimal("250.00")));
                        } catch (DaoException e) {
                            logger.error("Error", e);
                        }
                        return 0;
                    });

            int oilFilterId = partDao.findAll().stream()
                    .filter(p -> p.getName().equalsIgnoreCase("Oil filter"))
                    .map(Part::getId).findFirst()
                    .orElseGet(() -> {
                        try {
                            return partDao.create(new Part(0, "Oil filter", new BigDecimal("30.00"), 10));
                        } catch (DaoException e) {
                            logger.error("Error", e);
                            return 0;
                        }
                    });

            int oil5w30Id = partDao.findAll().stream()
                    .filter(p -> p.getName().equalsIgnoreCase("Motor oil 5W-30"))
                    .map(Part::getId).findFirst()
                    .orElseGet(() -> {
                        try {
                            return partDao.create(new Part(0, "Motor oil 5W-30", new BigDecimal("40.00"), 20));
                        } catch (DaoException e) {
                            logger.error("Error", e);
                            return 0;
                        }
                    });

            // 7) Add tasks and part the service order
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
                invoiceService.payInvoice(inv.getId(), Date.valueOf(LocalDate.now()), inv.getTotalAmount());
                logger.info("Invoice paid: {}", inv);
            } else {
                logger.warn("Invoice not found for order {}", orderId);
            }

        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
        }


        XmlService xmlService = new XmlService();


        String ownersXmlPath = "src/main/resources/xml/car_owner_details.xml";
        String estimatesXmlPath = "src/main/resources/xml/repair_time_estimate.xml";


        var owners = xmlService.loadCarOwnerDetails(ownersXmlPath);
        var estimates = xmlService.loadRepairEstimates(estimatesXmlPath);


        owners.forEach(o -> logger.info("Parsed from XML (Owner): {}", o));
        estimates.forEach(e -> logger.info("Parsed from XML (Estimate): {}", e));


    }
}
