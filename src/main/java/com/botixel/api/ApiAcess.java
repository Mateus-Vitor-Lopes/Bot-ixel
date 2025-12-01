package com.botixel.api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiAcess {

    private ApiKeyStore ApiKey;

    public ApiAcess(ApiKeyStore apiKey) {
        this.ApiKey = ApiKey;
    }

    public String encontrarItem() {

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.hypixel.net/v2/skyblock/auctions"))
                    .header("API-Key", this.ApiKey.getApiKey())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
