package com.example.RetailSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class GetItem {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Scanner console = new Scanner(System.in);
        System.out.println("Look for an item here. Would you like to see all items or loop up by DPCI?");
        System.out.print("Enter \"DPCI\" or \"all\": ");
        String input = console.nextLine().toLowerCase();

        if (input.equals("all")) {
            URI uri = new URI("http://localhost:8080/items");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
        } else {
            String dpci = getDpci(console);

            URI uri = new URI("http://localhost:8080/items/dpci/" + dpci);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
        }
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
}
