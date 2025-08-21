package org.example.car_service.dao.interfaces;

import org.example.car_service.model.Mechanic;

import java.util.List;
import java.util.Optional;

public interface MechanicDao {
    int create(Mechanic m);
    Optional<Mechanic> findById(int id);
    List<Mechanic> findAll();
    void update(Mechanic m);
    void delete(int id);
}
