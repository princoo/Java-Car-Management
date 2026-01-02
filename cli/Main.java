// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
package cli;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
   private static final HttpClient client = HttpClient.newHttpClient();
   private static final String BASE_URL = "http://localhost:8080/Java-Assignment";

   public Main() {
   }

   public static void main(String[] var0) throws Exception {
      if (var0.length == 0) {
         System.out.println("No command provided.");
      } else {
         String var1 = var0[0];
         Map var2 = parseArguments(var0);
         switch (var1) {
            case "create-car":
               handleCreateCar(var2);
               break;
            case "add-fuel":
               handleAddFuel(var2);
               break;
            case "fuel-stats":
               handleFuelStats(var2);
               break;
            default:
               System.out.println("Unknown command: " + var1);
         }

      }
   }

   private static void handleCreateCar(Map<String, String> var0) throws Exception {
      String var1 = String.format("{\"brand\":\"%s\", \"model\":\"%s\", \"year\":%s}", var0.get("--brand"), var0.get("--model"), var0.get("--year"));
      sendPostRequest("/api/cars", var1);
   }

   private static void handleAddFuel(Map<String, String> var0) throws Exception {
      String var1 = (String)var0.get("--carId");
      String var2 = String.format("{\"liters\":%s, \"price\":%s, \"odometer\":%s}", var0.get("--liters"), var0.get("--price"), var0.get("--odometer"));
      sendPostRequest("/api/cars/" + var1 + "/fuel", var2);
   }

   private static void handleFuelStats(Map<String, String> var0) throws Exception {
      String var1 = (String)var0.get("--carId");
      HttpRequest var2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/Java-Assignment/servlet/fuel-stats?carId=" + var1)).GET().build();
      HttpResponse var3 = client.send(var2, BodyHandlers.ofString());
      if (var3.statusCode() == 200) {
         formatAndPrintStats((String)var3.body());
      } else {
         System.out.println("Error: " + var3.statusCode());
         System.out.println((String)var3.body());
      }

   }

   private static void sendPostRequest(String var0, String var1) throws Exception {
      HttpRequest var2 = HttpRequest.newBuilder().uri(URI.create("http://localhost:8080/Java-Assignment" + var0)).header("Content-Type", "application/json").POST(BodyPublishers.ofString(var1)).build();
      HttpResponse var3 = client.send(var2, BodyHandlers.ofString());
      System.out.println("Response Code: " + var3.statusCode());
      System.out.println((String)var3.body());
   }

   private static Map<String, String> parseArguments(String[] var0) {
      HashMap var1 = new HashMap();

      for(int var2 = 1; var2 < var0.length; var2 += 2) {
         if (var2 + 1 < var0.length && var0[var2].startsWith("--")) {
            var1.put(var0[var2], var0[var2 + 1]);
         }
      }

      return var1;
   }

   private static void formatAndPrintStats(String var0) {
      String var1 = extractJsonValue(var0, "totalFuel");
      String var2 = extractJsonValue(var0, "totalCost");
      String var3 = extractJsonValue(var0, "averageConsumption");
      System.out.println("Total fuel: " + var1 + " L");
      System.out.println("Total cost: " + var2);
      System.out.println("Average consumption: " + var3 + " L/100km");
   }

   private static String extractJsonValue(String var0, String var1) {
      Pattern var2 = Pattern.compile("\"" + var1 + "\":\\s*([^,}\\]]+)");
      Matcher var3 = var2.matcher(var0);
      return var3.find() ? var3.group(1).replace("\"", "") : "0";
   }
}
