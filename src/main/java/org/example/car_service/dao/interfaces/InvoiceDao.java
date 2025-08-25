package org.example.car_service.dao.interfaces;

import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Invoice;

import java.util.*;

public interface InvoiceDao {
    int create(Invoice inv) throws DaoException;
    Optional<Invoice> findById(int id) throws DaoException;
    Optional<Invoice> findByServiceOrderId(int serviceOrderId) throws DaoException;
    List<Invoice> findAll() throws DaoException;
    void update(Invoice inv) throws DaoException;
    void delete(int id) throws DaoException;
}
