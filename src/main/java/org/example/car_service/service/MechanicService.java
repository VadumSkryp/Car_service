package org.example.car_service.service;

import org.example.car_service.dao.interfaces.MechanicDao;
import org.example.car_service.model.Mechanic;

import java.util.*;

public class MechanicService {
    private final MechanicDao mechanicDao;

    public MechanicService(MechanicDao mechanicDao) {
        this.mechanicDao = mechanicDao;
    }

    public int addMechanic(String name, String phone) {
        return mechanicDao.create(new Mechanic(0, name, phone));
    }

    public List<Mechanic> listMechanics() { return mechanicDao.findAll(); }

    public Optional<Mechanic> getMechanic(int id) { return mechanicDao.findById(id); }
}
