package ru.tinkoff.daria.test.restdataprovider.restclient;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;

public class RestService {

    private final String baseUrl;
    private HttpsURLConnection connection;

    public RestService(String https_url) {
        this.baseUrl = https_url;
    }

    public HttpsURLConnection getConnection(String method, String path) {
        try {
            URL url = new URL(baseUrl + path);
            this.connection = (HttpsURLConnection)url.openConnection();
            this.connection.setRequestMethod(method);
            this.connection.setRequestProperty("Content-Type","application/json");
            this.connection.setDoOutput(true);
            this.connection.setDoInput(true);
            return this.connection;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
