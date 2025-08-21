package org.example.car_service.service;

import org.example.car_service.dao.interfaces.CarDao;
import org.example.car_service.dao.interfaces.CustomerDao;
import org.example.car_service.model.Car;
import org.example.car_service.model.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerDao customerDao;
    private final CarDao carDao;

    public CustomerService(CustomerDao customerDao, CarDao carDao) {
        this.customerDao = customerDao;
        this.carDao = carDao;
    }

    public int registerCustomer(String name, String phone, String email) {
        return customerDao.create(new Customer(0, name, phone, email));
    }

    public List<Customer> listCustomers() { return customerDao.findAll(); }

    public Optional<Customer> getCustomer(int id) { return customerDao.findById(id); }

    public List<Car> getCustomerCars(int customerId) { return carDao.findByCustomerId(customerId); }
}
