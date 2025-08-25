package org.example.car_service.model;

public class CarModel {
    private int id;
    private String manufacturer;
    private String modelName;

    public CarModel(){}

    // Конструктор
    public CarModel(int id, String manufacturer, String modelName) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.modelName = modelName;
    }

    // Геттери і сеттери
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    // Для зручного виводу
    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }
}
