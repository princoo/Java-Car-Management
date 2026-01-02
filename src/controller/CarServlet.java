package controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import services.CarService;
import model.Car;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/api/cars")
public class CarServlet extends HttpServlet {

    private static final CarService carService = new CarService();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Car car = gson.fromJson(req.getReader(), Car.class);

        if (car == null ||
                car.getBrand() == null ||
                car.getModel() == null ||
                car.getYear() == 0) {

            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Missing parameters\"}");
            return;
        }

        Car createdCar = carService.createCar(
                car.getBrand(),
                car.getModel(),
                car.getYear());

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(createdCar));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        Collection<Car> cars = carService.getAllCars();
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(cars));
    }

    public static CarService getCarService() {
        return carService;
    }
}
