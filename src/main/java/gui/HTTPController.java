package gui;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.DataflowAnomalyAnalysis"})
public class HTTPController {

    public static HTTPController httpController;

    private static String serverUrl = "http://localhost:6969";
    public transient HttpClient httpClient;

    HTTPController() {
    }

    public static void initializeHTTPController() {
        httpController = new HTTPController();
        httpController.httpClient = HttpClient.newHttpClient();
        httpController.httpClient.followRedirects();
    }

    public static HTTPController getHTTPController() {
        return httpController;
    }

    HttpRequest.Builder makeRequest(String path, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder("?");
        for (Map.Entry<String, String> param : params.entrySet()) {
            stringBuilder.append(param.getKey()).append("=").append(param.getValue()).append("&");
        }
        String paramString = stringBuilder.toString();

        HttpRequest.Builder httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + path + paramString));

        return httpRequest;
    }

    HttpRequest makeGetRequest(String path, Map<String, String> params) {
        return makeRequest(path, params).GET().build();
    }

    HttpResponse<String> sendRequest(HttpRequest httpRequest) {
        HttpResponse<String> httpResponse;
        try {
            httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

        return httpResponse;
    }

}
