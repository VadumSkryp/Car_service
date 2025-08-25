package org.example.car_service.dao.interfaces;


import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.ServiceOrder;

import java.util.*;

public interface ServiceOrderDao {
    int create(ServiceOrder o) throws DaoException;
    Optional<ServiceOrder> findById(int id) throws DaoException;
    List<ServiceOrder> findAll() throws DaoException;
    List<ServiceOrder> findByCarId(int carId) throws DaoException;
    void update(ServiceOrder o) throws DaoException;
    void delete(int id) throws DaoException;
}
