package org.example.car_service.dao.interfaces;


import org.example.car_service.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    int create(Car c);
    Optional<Car> findById(int id);
    List<Car> findAll();
    List<Car> findByCustomerId(int customerId);
    void update(Car c);
    void delete(int id);
}
