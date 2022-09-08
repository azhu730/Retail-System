package com.example.RetailSystem;

import java.util.Scanner;

import com.example.RetailSystem.model.Item;
import com.example.RetailSystem.repository.ItemRepository;
import com.example.RetailSystem.controller.InventoryController;

public class AddItem {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Item newItem = new Item();
        String input = "";

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
    }

    public static int getDepartmentNo(Scanner console) {
        boolean contLoop = true;
        String departmentNoStr = "";

        while (contLoop) {
            System.out.print("Enter department number (Input 3 numbers between 000-999): ");
            String input = console.nextLine();

            int numNumerics = 0;
            if (input.length() == 3) {
                for (int i = 0; i < input.length(); i++) {
                    if (Character.isDigit(input.charAt(i))) {
                        departmentNoStr += input.charAt(i);
                        numNumerics++;
                    }
                }
            }

            if (numNumerics == 3) {
                contLoop = false;
            }

            if (contLoop) {
                System.out.println("Invalid department number. Try again.");
                departmentNoStr = "";
            }
        }

        return Integer.valueOf(departmentNoStr);
    }

    public static int getClassNo(Scanner console) {
        boolean contLoop = true;
        String classNoStr = "";

        while (contLoop) {
            System.out.print("Enter class number (Input 2 numbers between 00-99): ");
            String input = console.nextLine();

            int numNumerics = 0;
            if (input.length() == 2) {
                for (int i = 0; i < input.length(); i++) {
                    if (Character.isDigit(input.charAt(i))) {
                        classNoStr += input.charAt(i);
                        numNumerics++;
                    }
                }
            }

            if (numNumerics == 2) {
                contLoop = false;
            }

            if (contLoop) {
                System.out.println("Invalid class number. Try again.");
                classNoStr = "";
            }
        }

        return Integer.valueOf(classNoStr);
    }

    public static int getItemNo(Scanner console) {
        boolean contLoop = true;
        String itemNoStr = "";

        while (contLoop) {
            System.out.print("Enter item number (Input 4 numbers between 0000-9999): ");
            String input = console.nextLine();

            int numNumerics = 0;
            if (input.length() == 4) {
                for (int i = 0; i < input.length(); i++) {
                    if (Character.isDigit(input.charAt(i))) {
                        itemNoStr += input.charAt(i);
                        numNumerics++;
                    }
                }
            }

            if (numNumerics == 4) {
                contLoop = false;
            }

            if (contLoop) {
                System.out.println("Invalid item number. Try again.");
                itemNoStr = "";
            }
        }

        return Integer.valueOf(itemNoStr);
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
        String input = console.nextLine();
        return input;
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

        return Integer.valueOf(input);
    }

    public static boolean getOnSale(Scanner console) {
        boolean contLoop = true;

        while (contLoop) {
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

        return false;
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
