package model;

import java.util.ArrayList;
import java.util.List;

public class Car {
    private int id;
    private String brand;
    private String model;
    private int year;
    private List<FuelEntry> fuelEntries = new ArrayList<>();

    public Car(int id, String brand, String model, int year) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
    }

    public int getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public List<FuelEntry> getFuelEntries() { return fuelEntries; }

    public void addFuelEntry(FuelEntry entry) {
        fuelEntries.add(entry);
    }
}
