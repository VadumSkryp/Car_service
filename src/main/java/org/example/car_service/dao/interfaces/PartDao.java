package org.example.car_service.dao.interfaces;

import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Part;

import java.util.*;

public interface PartDao {
    int create(Part p) throws DaoException;
    Optional<Part> findById(int id) throws DaoException;
    List<Part> findAll() throws DaoException;
    void update(Part p) throws DaoException;
    void delete(int id) throws DaoException;
}
