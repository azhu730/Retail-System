package com.example.RetailSystem.inventory_application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.json.*;

public class EditItem {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        Scanner console = new Scanner(System.in);
        System.out.println("Edit an item's properties here.");

        String dpci = getDpci(console);
        String itemJson = getJson(dpci);

        System.out.println(itemJson);
        System.out.println("What would you like to edit?");

        Set<String> validResponses = getValidResponses();

        Set<String> requests = getRequest(console, validResponses);
        int tcin = getTcin(itemJson);

        executeRequest(console, requests, tcin);
    }

    public static String getDpci(Scanner console) {
        boolean contLoop = true;
        StringBuilder dpciParsed = new StringBuilder();

        while (contLoop) {
            System.out.print("Enter item DPCI: ");
            String dpci = console.nextLine();

            for (int i = 0; i < dpci.length(); i++) {
                if (Character.isDigit(dpci.charAt(i))) {
                    dpciParsed.append(dpci.charAt(i));
                }
            }
            if (dpciParsed.length() == 9) {
                contLoop = false;
            } else {
                System.out.println("Invalid DPCI. Try again.");
                dpciParsed = new StringBuilder();
            }
        }

        return dpciParsed.toString();
    }

    public static String getJson(String dpci) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI("http://localhost:8080/items/dpci/" + dpci);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public static Set<String> getValidResponses() {
        Set<String> validResponses = new HashSet<>();
        validResponses.add("departmentno");
        validResponses.add("classno");
        validResponses.add("itemno");
        validResponses.add("upc");
        validResponses.add("name");
        validResponses.add("price");
        validResponses.add("onhand");
        // validResponses.add("onsale");
        validResponses.add("floorlocation");
        validResponses.add("done");

        return validResponses;
    }

    public static Set<String> getRequest(Scanner console, Set<String> validResponses) {
        boolean contLoop = true;
        Set<String> request = new HashSet<>();
        Set<String> added = new HashSet<>();

        while (contLoop) {
            System.out.print("Enter one from the following: departmentNo, classNo, itemNo, upc, name, " +
                    "price, onHand, floorLocation (Enter \"done\" if done): ");
            String singleRequest = console.nextLine().toLowerCase();

            if (!validResponses.contains(singleRequest)) {
                System.out.println("Invalid response. Try again.");
            } else if (singleRequest.equals("done")) {
                contLoop = false;
            } else {
                if (added.contains(singleRequest)) {
                    System.out.println("Property already edited. Enter another property.");
                } else {
                    request.add(singleRequest);
                    added.add(singleRequest);
                    System.out.println("Success. Add another property or enter \"done\".");
                }
            }
        }

        return request;
    }

    public static void executeRequest(Scanner console, Set<String> requests, int tcin) throws URISyntaxException, IOException, InterruptedException {
        StringBuilder input = new StringBuilder("{");

        int count = 0;
        for (String r : requests) {
            count++;

            if (r.equals("upc")) {
                String newUpc = getUPC(console);
                input.append("\"upc\":\"").append(newUpc).append("\"");
            }

            if (r.equals("name")) {
                System.out.print("Enter new name: ");
                String newName = console.nextLine();
                input.append("\"name\":\"").append(newName).append("\"");
            }

            if (r.equals("price")) {
                double newPrice = getPrice(console);
                input.append("\"price\":").append(newPrice);
            }

            if (r.equals("onhand")) {
                int newOnHand = getOnHand(console);
                input.append("\"onHand\":").append(newOnHand);
            }

            if (r.equals("floorlocation")) {
                String newFloorLocation = getFloorLocation(console);
                input.append("\"floorLocation\":\"").append(newFloorLocation).append("\"");
            }

            if (count != requests.size()) {
                input.append(",");
            }
        }

        input.append("}");

        URI uri = new URI("http://localhost:8080/items/tcin/" + tcin);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(input.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static int getTcin(String itemJson) {
        JSONObject obj = new JSONObject(itemJson);
        return obj.getInt("tcin");
    }

    public static String getUPC(Scanner console) {
        boolean contLoop = true;
        String input = "";

        while (contLoop) {
            System.out.print("Enter item's new UPC: ");
            input = console.nextLine();
            boolean allNumeric = true;

            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    allNumeric = false;
                }
            }

            if (allNumeric && input.length() == 12) {
                contLoop = false;
            } else {
                System.out.println("Invalid UPC. Try again.");
                input = "";
            }
        }

        return input;
    }

    public static double getPrice(Scanner console) {
        boolean contLoop = true;
        String input = "";

        while (contLoop) {
            System.out.print("Enter new price: ");
            input = console.nextLine();

            boolean allNumeric = true;
            int decimalCount = 0;

            for (int i = 0; i < input.length(); i++) {
                allNumeric = Character.isDigit(input.charAt(i));

                if (input.charAt(i) == '.') {
                    decimalCount++;
                }
            }

            if (allNumeric && decimalCount == 1) {
                contLoop = false;
            } else {
                System.out.println("Invalid price. Try again.");
            }
        }

        return Double.parseDouble(input);
    }

    public static int getOnHand(Scanner console) {
        boolean contLoop = true;
        String input = "";

        while (contLoop) {
            System.out.print("Enter new on hand count: ");
            input = console.nextLine();
            boolean allNumeric = true;

            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    allNumeric = false;
                }
            }

            if (allNumeric) {
                contLoop = false;
            } else {
                System.out.println("Invalid response. Try again.");
                input = "";
            }
        }

        return Integer.parseInt(input);
    }

    public static String getFloorLocation(Scanner console) {
        boolean contLoop = true;
        String input = "";

        while (contLoop) {
            System.out.print("Enter new floor location of item: ");
            input = console.nextLine();

            boolean checkValid = input.length() == 3;

            if (input.length() == 3) {
                if (!Character.isLetter(input.charAt(0))) {
                    checkValid = false;
                }

                if (!Character.isDigit(input.charAt(1))) {
                    checkValid = false;
                }

                if (!Character.isDigit(input.charAt(2))) {
                    checkValid = false;
                }
            }

            if (checkValid) {
                contLoop = false;
            } else {
                System.out.println("Invalid floor location. Try again.");
                input = "";
            }
        }

        return input;
    }
}
