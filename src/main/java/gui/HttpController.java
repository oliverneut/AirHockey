package gui;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import game.MatchSocketHandler;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.util.HttpCookieStore;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.client.WebSocketUpgradeRequest;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.DataflowAnomalyAnalysis"})
public class HttpController {

    //ws endpoint
    public static URI wsUri = URI.create("ws://localhost:6969/match");
    //server url
    private static URI httpUri = URI.create("http://localhost:6969");

    //only instance of HttpController
    private static HttpController controller = null;
    private static WebSocketClient webSocketClient;

    //fields of HttpController
    private transient HttpClient httpClient;

    private HttpController() {
        httpClient = new HttpClient();

        httpClient.setFollowRedirects(true);
        httpClient.setCookieStore(new HttpCookieStore());

        try {
            httpClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get instance of HttpController.
     *
     * @return Instance of HttpController
     */
    public static HttpController getHTTPController() {
        if (controller == null) {
            controller = new HttpController();
        }
        return controller;
    }

    /**
     * Return a WebSocketClient using the defaults from HttpClient.
     */
    public static void initializeWebSocket(MatchSocketHandler localEndpoint)
            throws Exception {
        if (webSocketClient == null) {
            webSocketClient = new WebSocketClient(getHTTPController().httpClient);
            webSocketClient.start();
            webSocketClient.setCookieStore(controller.httpClient.getCookieStore());

            WebSocketUpgradeRequest webSocketUpgradeRequest = new WebSocketUpgradeRequest(
                    webSocketClient, controller.httpClient, wsUri, localEndpoint);

            webSocketClient.connect(localEndpoint, URI.create(wsUri + "?user=" + Main.username),
                    new ClientUpgradeRequest(webSocketUpgradeRequest));
        }

    }

    public ContentResponse getRequest(String path) {
        return getRequest(path, new HashMap<>());
    }

    /**
     * Make a GET request to the server and return the response.
     *
     * @param path   Path on which to make request.
     * @param params Query params to add to request.
     * @return Response from server or null if exception thrown.
     */
    public ContentResponse getRequest(String path, Map<String, String> params) {
        Request request = new HttpRequestBuilder(httpClient)
                .setRoute(path)
                .setParams(params)
                .build();

        ContentResponse response;
        try {
            response = request.send();
        } catch (InterruptedException | TimeoutException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    /**
     * Convert the ContentResponse to JsonObject.
     *
     * @param response ContentResponse to be parsed.
     * @return Obtained JsonObject.
     */
    public JsonObject responseToJson(ContentResponse response) {
        if (response == null) {
            return null;
        }

        try {
            return (JsonObject) Jsoner.deserialize(response.getContentAsString());

        } catch (JsonException e) {
            e.printStackTrace();
            JsonObject malformedResponse = new JsonObject();
            malformedResponse.put("Head", "Error");
            malformedResponse.put("Error", response.getContentAsString());
            return malformedResponse;
        }
    }

    interface RequestBuilder {
        RequestBuilder setRoute(String route);

        RequestBuilder setMethod(HttpMethod httpMethod);

        RequestBuilder setParam(String name, String value);

        RequestBuilder setParams(Map<String, String> params);

        Request build();
    }

    public static class HttpRequestBuilder implements RequestBuilder {

        transient Request request;

        public HttpRequestBuilder(HttpClient httpClient) {
            this.request = httpClient.newRequest(httpUri);
        }

        @Override
        public RequestBuilder setRoute(String route) {
            this.request.path(route);
            return this;
        }

        @Override
        public RequestBuilder setMethod(HttpMethod httpMethod) {
            this.request.method(httpMethod);
            return this;
        }

        @Override
        public RequestBuilder setParam(String name, String value) {
            this.request.param(name, value);
            return this;
        }

        @Override
        public RequestBuilder setParams(Map<String, String> params) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                this.request.param(entry.getKey(), entry.getValue());
            }
            return this;
        }

        @Override
        public Request build() {
            return this.request;
        }
    }

}
