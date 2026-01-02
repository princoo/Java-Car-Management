package servlet;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Car;
import model.FuelEntry;
import services.CarService;
import controller.CarServlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/servlet/fuel-stats")
public class FuelStatsServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final CarService carService = CarServlet.getCarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String carIdStr = req.getParameter("carId");
        if (carIdStr == null) {
            resp.setStatus(400);
            resp.getWriter().write("{\"error\":\"carId required\"}");
            return;
        }

        int carId = Integer.parseInt(carIdStr);
        Car car = carService.getCar(carId);

        if (car == null) {
            resp.setStatus(404);
            resp.getWriter().write("{\"error\":\"Car not found\"}");
            return;
        }

        double totalLiters = 0;
        double totalCost = 0;

        for (FuelEntry f : car.getFuelEntries()) {
            totalLiters += f.getLiters();
            totalCost += f.getPrice();
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("carId", carId);
        stats.put("totalLiters", totalLiters);
        stats.put("totalCost", totalCost);

        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(stats));
    }
}
