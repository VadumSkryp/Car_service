package org.example.car_service.dao.interfaces;


import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Customer;

import java.util.*;

public interface CustomerDao {
    int create(Customer c) throws DaoException;
    Optional<Customer> findById(int id) throws DaoException;
    List<Customer> findAll() throws DaoException;
    void update(Customer c) throws DaoException;
    void delete(int id) throws DaoException;
}
