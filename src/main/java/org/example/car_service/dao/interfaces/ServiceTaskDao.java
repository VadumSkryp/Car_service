package org.example.car_service.dao.interfaces;

import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.ServiceTask;

import java.util.List;
import java.util.Optional;

public interface ServiceTaskDao {
    int create(ServiceTask t) throws DaoException;
    Optional<ServiceTask> findById(int id) throws DaoException;
    List<ServiceTask> findAll() throws DaoException;
    void update(ServiceTask t) throws DaoException;
    void delete(int id) throws DaoException;
}
