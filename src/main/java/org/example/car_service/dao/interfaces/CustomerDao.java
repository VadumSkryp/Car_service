package org.example.car_service.dao.interfaces;


import org.example.car_service.model.Customer;

import java.util.*;

public interface CustomerDao {
    int create(Customer c);
    Optional<Customer> findById(int id);
    List<Customer> findAll();
    void update(Customer c);
    void delete(int id);
}
