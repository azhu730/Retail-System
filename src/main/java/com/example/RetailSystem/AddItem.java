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

        int departmentNo = getDepartmentNo(console);
        newItem.setDepartmentNo(departmentNo);

        int classNo = getClassNo(console);
        newItem.setClassNo(classNo);

        int itemNo = getItemNo(console);
        newItem.setItemNo(itemNo);

        Long dpci = getDPCI(departmentNo, classNo, itemNo);
        newItem.setDpci(dpci);

        String name = getName(console);
        newItem.setName(name);

        Double price = getPrice(console);
        newItem.setPrice(price);
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
            System.out.print("Enter department number (Input 2 numbers between 00-99): ");
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
            System.out.print("Enter department number (Input 4 numbers between 0000-9999): ");
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
                System.out.println("Invalid class number. Try again.");
                itemNoStr = "";
            }
        }

        return Integer.valueOf(itemNoStr);
    }

    public static Long getDPCI(int departmentNo, int classNo, int itemNo) {
        String deptPadded = String.format("%03d" , departmentNo);
        String classPadded = String.format("%02d" , classNo);
        String itemPadded = String.format("%04d" , itemNo);

        System.out.println("Item DPCI: " + deptPadded + "-" + classPadded + "-" + itemPadded);

        return Long.valueOf(deptPadded + classPadded + itemPadded);
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

}