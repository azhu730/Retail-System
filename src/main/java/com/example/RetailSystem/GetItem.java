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
//        Scanner console = new Scanner(System.in);
//        System.out.println("Look for the item by DPCI: ");
//        String dpci = console.nextLine();

        URI uri = new URI("http://localhost:8080/items");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}
