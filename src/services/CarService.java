package services;

import model.Car;
import model.FuelEntry;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CarService {

    private final Map<Integer, Car> cars = new ConcurrentHashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public Car createCar(String brand, String model, int year) {
        int id = idGenerator.getAndIncrement();
        Car car = new Car(id, brand, model, year);
        cars.put(id, car);
        return car;
    }

    public Collection<Car> getAllCars() {
        return cars.values();
    }

    public Car getCar(int id) {
        return cars.get(id);
    }

    public boolean addFuel(int carId, FuelEntry entry) {
        Car car = cars.get(carId);
        if (car == null) return false;
        car.addFuelEntry(entry);
        return true;
    }
}
