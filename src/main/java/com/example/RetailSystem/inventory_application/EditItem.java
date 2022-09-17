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

        String request = getRequest(console, validResponses);
        int tcin = getTcin(itemJson);

        executeRequest(console, request, tcin);
    }

    public static String getDpci(Scanner console) {
        boolean contLoop = true;
        String dpciParsed = "";

        while (contLoop) {
            System.out.print("Enter item DPCI: ");
            String dpci = console.nextLine();

            for (int i = 0; i < dpci.length(); i++) {
                if (Character.isDigit(dpci.charAt(i))) {
                    dpciParsed += dpci.charAt(i);
                }
            }
            if (dpciParsed.length() == 9) {
                contLoop = false;
            } else {
                System.out.println("Invalid DPCI. Try again.");
                dpciParsed = "";
            }
        }

        return dpciParsed;
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
        validResponses.add("onsale");
        validResponses.add("floorlocation");

        return validResponses;
    }

    public static String getRequest(Scanner console, Set<String> validResponses) {
        boolean contLoop = true;
        String request = "";

        while (contLoop) {
            System.out.print("Choose from the following: departmentNo, classNo, itemNo, upc, name, " +
                    "price, onHand, onSale, floorLocation: ");
            request = console.nextLine().toLowerCase();

            if (!validResponses.contains(request)) {
                request = "";
                System.out.println("Invalid response. Try again.");
            } else {
                contLoop = false;
            }
        }

        return request;
    }

    public static void executeRequest(Scanner console, String changeRequest, int tcin) throws URISyntaxException, IOException, InterruptedException {
        String input = "{";

        if (changeRequest.equals("name")) {
            System.out.print("Enter new name: ");
            String newName = console.nextLine();
            input += "\"name\":\"" + newName + "\"";
        }

        input += "}";

        URI uri = new URI("http://localhost:8080/items/tcin/" + tcin);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(input))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static int getTcin(String itemJson) {
        JSONObject obj = new JSONObject(itemJson);
        return obj.getInt("tcin");
    }
}
