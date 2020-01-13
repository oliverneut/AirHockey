package gui;

import java.net.HttpCookie;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.HttpCookieStore;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.DataflowAnomalyAnalysis"})
public class HTTPController {

    public static HTTPController controller;
    public static URI serverUri = URI.create("http://localhost:6969");

    public transient HttpClient httpClient;
    transient String sessionId = "0";

    /**
     * Initializes the singleton HTTPController.
     */
    public static void initializeHTTPController() {
        controller = new HTTPController();

        controller.httpClient = new HttpClient();

        controller.httpClient.setFollowRedirects(true);
        controller.httpClient.setCookieStore(new HttpCookieStore());

        controller.httpClient.getCookieStore()
                .add(serverUri, new HttpCookie("JSESSIONID", controller.sessionId));

        try {
            controller.httpClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HTTPController getHTTPController() {
        return controller;
    }

    Request makeRequest(String path, Map<String, String> params) {
        Request request = httpClient.newRequest(serverUri + path);
        for (Map.Entry<String, String> param : params.entrySet()) {
            request.param(param.getKey(), param.getValue());
        }
        request.timeout(5, TimeUnit.SECONDS);

        HttpCookie cookie = httpClient.getCookieStore().get(serverUri)
                .get(0);
        request.cookie(cookie);

        return request;
    }

    Request makeGetRequest(String path, Map<String, String> params) {
        return makeRequest(path, params).method(HttpMethod.GET);
    }

    ContentResponse sendRequest(Request request) {
        ContentResponse response;
        try {
            System.out.println(request.toString());

            response = request.send();

            System.out.println(response.getContentAsString());
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }

        return response;
    }

}
