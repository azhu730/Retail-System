package com.example.RetailSystem.inventory_application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.example.RetailSystem.model.Item;

public class AddItem {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Item newItem = makeItem();

        String itemBody = "";
        itemBody += "{\"dpci\":\"" + newItem.getDpci() + "\",";
        itemBody += "\"departmentNo\":" + newItem.getDepartmentNo() + ",";
        itemBody += "\"classNo\":" + newItem.getClassNo() + ",";
        itemBody += "\"itemNo\":" + newItem.getItemNo() + ",";
        itemBody += "\"upc\":\"" + newItem.getUpc() + "\",";
        itemBody += "\"name\":\"" + newItem.getName() + "\",";
        itemBody += "\"price\":" + newItem.getPrice() + ",";
        itemBody += "\"onHand\":" + newItem.getOnHand() + ",";
        itemBody += "\"onSale\":" + newItem.getOnSale() + ",";
        itemBody += "\"floorLocation\":\"" + newItem.getFloorLocation() + "\"}";

        URI uri = new URI("http://localhost:8080/items");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(itemBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static Item makeItem() {
        Scanner console = new Scanner(System.in);
        Item newItem = new Item();

        System.out.println("Please input the information of your item:");

        String upc = getUPC(console);
        newItem.setUpc(upc);

        int departmentNo = getDepartmentNo(console);
        newItem.setDepartmentNo(departmentNo);

        int classNo = getClassNo(console);
        newItem.setClassNo(classNo);

        int itemNo = getItemNo(console);
        newItem.setItemNo(itemNo);

        String dpci = getDPCI(departmentNo, classNo, itemNo);
        newItem.setDpci(dpci);

        String name = getName(console);
        newItem.setName(name);

        double price = getPrice(console);
        newItem.setPrice(price);

        String floorLocation = getFloorLocation(console);
        newItem.setFloorLocation(floorLocation);

        int onHand = getOnHand(console);
        newItem.setOnHand(onHand);

        boolean onSale = getOnSale(console);
        newItem.setOnSale(onSale);

        return newItem;
    }

    public static int getDepartmentNo(Scanner console) {
        boolean contLoop = true;
        StringBuilder departmentNoStr = new StringBuilder();

        while (contLoop) {
            System.out.print("Enter department number (Input 3 numbers between 000-999): ");
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
            System.out.print("Enter class number (Input 2 numbers between 00-99): ");
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
            System.out.print("Enter item number (Input 4 numbers between 0000-9999): ");
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

    public static String getDPCI(int departmentNo, int classNo, int itemNo) {
        String deptPadded = String.format("%03d" , departmentNo);
        String classPadded = String.format("%02d" , classNo);
        String itemPadded = String.format("%04d" , itemNo);

        System.out.println("Item DPCI: " + deptPadded + "-" + classPadded + "-" + itemPadded);

        return deptPadded + classPadded + itemPadded;
    }

    public static String getName(Scanner console) {
        System.out.print("Item Name: ");
        return console.nextLine();
    }

    public static double getPrice(Scanner console) {
        boolean contLoop = true;
        String input = "";

        while (contLoop) {
            System.out.print("Price: ");
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
            System.out.print("Enter on hand count: ");
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

    public static boolean getOnSale(Scanner console) {
        while (true) {
            System.out.print("Is this item on sale? (Y/N): ");

            String input = console.nextLine().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid response. Try again");
            }
        }
    }

    public static String getFloorLocation(Scanner console) {
        boolean contLoop = true;
        String input = "";

        while (contLoop) {
            System.out.print("Enter floor location of item: ");
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

    public static String getUPC(Scanner console) {
        boolean contLoop = true;
        String input = "";

        while (contLoop) {
            System.out.print("Enter item's UPC: ");
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
}
