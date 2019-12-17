package gui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@SuppressWarnings("checkstyle:AbbreviationAsWordInName")
public class HTTPController {

    private static String serverUrl = "http://localhost:6969";
    HttpClient httpClient;

    HTTPController() {
        this.httpClient = HttpClient.newHttpClient();
        this.httpClient.followRedirects();
    }

    HttpRequest makeRequest(String path, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder("?");
        for (Map.Entry<String, String> param : params.entrySet()) {
            stringBuilder.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        String paramString = stringBuilder.toString();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path + paramString))
                .GET()
                .build();

        return httpRequest;
    }

    String sendRequest(HttpRequest httpRequest) {
        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return httpResponse.body();
    }

}
