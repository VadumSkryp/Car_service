package org.example.car_service.dao.interfaces;

import org.example.car_service.model.Part;

import java.util.*;

public interface PartDao {
    int create(Part p);
    Optional<Part> findById(int id);
    List<Part> findAll();
    void update(Part p);
    void delete(int id);
}
