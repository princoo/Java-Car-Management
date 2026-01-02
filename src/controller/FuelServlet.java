package controller;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.FuelEntry;
import model.FuelEntryRequest;
import services.CarService;

import java.io.IOException;

@WebServlet("/api/cars/fuel")
public class FuelServlet extends HttpServlet {

    private final Gson gson = new Gson();
    private final CarService carService = CarServlet.getCarService();

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException {

    FuelEntryRequest fuelReq =
            gson.fromJson(req.getReader(), FuelEntryRequest.class);

    if (fuelReq == null) {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write("{\"error\":\"Invalid JSON\"}");
        return;
    }

    boolean success = carService.addFuel(
            fuelReq.carId,
            new FuelEntry(
                    fuelReq.liters,
                    fuelReq.price,
                    fuelReq.odometer
            )
    );

    if (!success) {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        resp.getWriter().write("{\"error\":\"Car not found\"}");
        return;
    }

    resp.setContentType("application/json");
    resp.getWriter().write("{\"status\":\"Fuel added\"}");
}

}
