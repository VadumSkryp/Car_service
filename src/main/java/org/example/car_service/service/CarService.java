package org.example.car_service.service;

import org.example.car_service.dao.interfaces.CarDao;
import org.example.car_service.dao.interfaces.CarModelDao;
import org.example.car_service.exceptions.DaoException;
import org.example.car_service.model.Car;
import org.example.car_service.model.CarModel;

import java.util.List;
import java.util.Optional;

public class CarService {
    private final CarDao carDao;
    private final CarModelDao carModelDao;

    public CarService(CarDao carDao, CarModelDao carModelDao) {
        this.carDao = carDao;
        this.carModelDao = carModelDao;
    }

    public int addCarForCustomer(int customerId, int carModelId, String plate, int year) throws DaoException {
        return carDao.create(new Car(0, customerId, carModelId, plate, year));
    }

    public List<Car> listAllCars() throws DaoException { return carDao.findAll(); }

    public Optional<Car> getCar(int id) throws DaoException { return carDao.findById(id); }

    public List<CarModel> listCarModels() throws DaoException { return carModelDao.findAll(); }
}
