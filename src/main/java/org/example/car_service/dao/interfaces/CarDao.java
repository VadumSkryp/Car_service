package org.example.car_service.dao.interfaces;


import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    int create(Car c) throws DaoException;
    Optional<Car> findById(int id) throws DaoException;
    List<Car> findAll() throws DaoException;
    List<Car> findByCustomerId(int customerId) throws DaoException;
    void update(Car c) throws DaoException;
    void delete(int id) throws DaoException;

    Optional<Car> findByLicensePlate(String plate) throws DaoException;
}
