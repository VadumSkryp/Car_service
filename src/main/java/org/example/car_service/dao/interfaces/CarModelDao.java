package org.example.car_service.dao.interfaces;

import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelDao {
    int create(CarModel m) throws DaoException;
    Optional<CarModel> findById(int id) throws DaoException;
    List<CarModel> findAll() throws DaoException;
    void update(CarModel m) throws DaoException;
    void delete(int id) throws DaoException;
}
