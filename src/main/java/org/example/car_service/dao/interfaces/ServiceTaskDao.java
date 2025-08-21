package org.example.car_service.dao.interfaces;

import org.example.car_service.model.ServiceTask;

import java.util.List;
import java.util.Optional;

public interface ServiceTaskDao {
    int create(ServiceTask t);
    Optional<ServiceTask> findById(int id);
    List<ServiceTask> findAll();
    void update(ServiceTask t);
    void delete(int id);
}
