package com.example.RetailSystem.inventory_application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class DeleteItem {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner console = new Scanner(System.in);
        System.out.println("Delete an item here.");

        String dpci = getDpci(console);
        URI uri = new URI("http://localhost:8080/items/dpci/" + dpci);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
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
}
