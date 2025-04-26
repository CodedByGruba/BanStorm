package de.codedbygruba.banStorm.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MojangAPI {
    private static MojangAPI instance;

    public static MojangAPI getInstance() {
        if (instance == null) {
            instance = new MojangAPI();
        }
        return instance;
    }

    public String getUUID(String playerName) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + playerName))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                return json.get("id").getAsString();
            } else if (response.statusCode() == 204) {
                return null;
            } else {
                System.err.println("Can't get UUID from Offline Player: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}