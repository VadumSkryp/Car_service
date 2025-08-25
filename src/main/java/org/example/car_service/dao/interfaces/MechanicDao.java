package org.example.car_service.dao.interfaces;

import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Mechanic;

import java.util.List;
import java.util.Optional;

public interface MechanicDao {
    int create(Mechanic m) throws DaoException;
    Optional<Mechanic> findById(int id) throws DaoException;
    List<Mechanic> findAll() throws DaoException;
    void update(Mechanic m) throws DaoException;
    void delete(int id) throws DaoException;
}
