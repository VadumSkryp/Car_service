package org.example.car_service.dao.interfaces;


import org.example.car_service.model.ServiceOrder;

import java.util.*;

public interface ServiceOrderDao {
    int create(ServiceOrder o);
    Optional<ServiceOrder> findById(int id);
    List<ServiceOrder> findAll();
    List<ServiceOrder> findByCarId(int carId);
    void update(ServiceOrder o);
    void delete(int id);
}
