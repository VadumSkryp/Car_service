package org.example.car_service.dao.interfaces;

import org.example.car_service.model.Invoice;

import java.util.*;

public interface InvoiceDao {
    int create(Invoice inv);
    Optional<Invoice> findById(int id);
    Optional<Invoice> findByServiceOrderId(int serviceOrderId);
    List<Invoice> findAll();
    void update(Invoice inv);
    void delete(int id);
}
