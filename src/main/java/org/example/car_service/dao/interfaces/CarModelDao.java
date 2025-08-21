package org.example.car_service.dao.interfaces;

import org.example.car_service.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelDao {
    int create(CarModel m);
    Optional<CarModel> findById(int id);
    List<CarModel> findAll();
    void update(CarModel m);
    void delete(int id);
}
