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

        executeRequest(console, requests, itemJson);
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

    public static void executeRequest(Scanner console, Set<String> requests, String itemJson) throws URISyntaxException, IOException, InterruptedException {
        JSONObject obj = new JSONObject(itemJson);

        StringBuilder input = new StringBuilder("{");

        boolean changeDpci = false;
        int deptNo = obj.getInt("departmentNo");
        int classNo = obj.getInt("classNo");
        int itemNo = obj.getInt("itemNo");

        for (String r : requests) {
            if (r.equals("departmentno")) {
                int newDeptNo = getDepartmentNo(console);
                input.append("\"departmentNo\":").append(newDeptNo);

                deptNo = newDeptNo;
                changeDpci = true;
            }

            if (r.equals("classno")) {
                int newClassNo = getClassNo(console);
                input.append("\"classNo\":").append(newClassNo);

                classNo = newClassNo;
                changeDpci = true;
            }

            if (r.equals("itemno")) {
                int newItemNo = getItemNo(console);
                input.append("\"itemNo\":").append(newItemNo);

                itemNo = newItemNo;
                changeDpci = true;
            }

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

            input.append(",");
        }

        if (changeDpci) {
            String newDpci = getDPCIStr(deptNo, classNo, itemNo);
            input.append("\"dpci\":\"").append(newDpci).append("\"");
        } else {
            input = new StringBuilder(input.substring(0, input.length() - 1));
        }

        input.append("}");

        URI uri = new URI("http://localhost:8080/items/tcin/" + obj.getInt("tcin"));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(input.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static int getDepartmentNo(Scanner console) {
        boolean contLoop = true;
        StringBuilder departmentNoStr = new StringBuilder();

        while (contLoop) {
            System.out.print("Enter new department number (Input 3 numbers between 000-999): ");
            String input = console.nextLine();

            int numNumerics = 0;
            if (input.length() == 3) {
                for (int i = 0; i < input.length(); i++) {
                    if (Character.isDigit(input.charAt(i))) {
                        departmentNoStr.append(input.charAt(i));
                        numNumerics++;
                    }
                }
            }

            if (numNumerics == 3) {
                contLoop = false;
            }

            if (contLoop) {
                System.out.println("Invalid department number. Try again.");
                departmentNoStr = new StringBuilder();
            }
        }

        return Integer.parseInt(departmentNoStr.toString());
    }

    public static int getClassNo(Scanner console) {
        boolean contLoop = true;
        StringBuilder classNoStr = new StringBuilder();

        while (contLoop) {
            System.out.print("Enter new class number (Input 2 numbers between 00-99): ");
            String input = console.nextLine();

            int numNumerics = 0;
            if (input.length() == 2) {
                for (int i = 0; i < input.length(); i++) {
                    if (Character.isDigit(input.charAt(i))) {
                        classNoStr.append(input.charAt(i));
                        numNumerics++;
                    }
                }
            }

            if (numNumerics == 2) {
                contLoop = false;
            }

            if (contLoop) {
                System.out.println("Invalid class number. Try again.");
                classNoStr = new StringBuilder();
            }
        }

        return Integer.parseInt(classNoStr.toString());
    }

    public static int getItemNo(Scanner console) {
        boolean contLoop = true;
        StringBuilder itemNoStr = new StringBuilder();

        while (contLoop) {
            System.out.print("Enter new item number (Input 4 numbers between 0000-9999): ");
            String input = console.nextLine();

            int numNumerics = 0;
            if (input.length() == 4) {
                for (int i = 0; i < input.length(); i++) {
                    if (Character.isDigit(input.charAt(i))) {
                        itemNoStr.append(input.charAt(i));
                        numNumerics++;
                    }
                }
            }

            if (numNumerics == 4) {
                contLoop = false;
            }

            if (contLoop) {
                System.out.println("Invalid item number. Try again.");
                itemNoStr = new StringBuilder();
            }
        }

        return Integer.parseInt(itemNoStr.toString());
    }

    public static String getDPCIStr(int departmentNo, int classNo, int itemNo) {
        String deptPadded = String.format("%03d" , departmentNo);
        String classPadded = String.format("%02d" , classNo);
        String itemPadded = String.format("%04d" , itemNo);

        System.out.println("Item DPCI: " + deptPadded + "-" + classPadded + "-" + itemPadded);

        return deptPadded + classPadded + itemPadded;
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
