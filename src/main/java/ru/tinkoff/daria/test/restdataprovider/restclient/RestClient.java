package ru.tinkoff.daria.test.restdataprovider.restclient;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestClient {

    private final RestService service;
    private final ObjectMapper mapper;

    public RestClient(String url) throws IOException {
        service = new RestService(url);
        mapper = configuremapper();
    }

    private ObjectMapper configuremapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    public <T> RestResponse<T> get(String path, Class<T> rspClass) throws IOException {
        return get(path, rspClass, null);
    }

    public <T> RestResponse<T> get(String path, Class<T> rspClass, Map<String, String> params) throws IOException {
        String p = path;
        if (params != null) {
            p = p + "/?";

            List<String> paramPairs = new ArrayList<>();
            for(Map.Entry<String, String> entry : params.entrySet()) {
                paramPairs.add(String.format("%s=%s", entry.getKey(), entry.getValue()));
            }

            p = p + String.join("&", paramPairs);
        }

        HttpsURLConnection con = service.getConnection("GET", p);

        if (con != null)
            return formRsp(rspClass, con);
        else return null;
    }

    public <T, R> RestResponse<T> post(R req, String path, Class<T> rspClass, String ... params) {
        return null;
    }

    private <T> RestResponse<T> formRsp(Class<T> rspClass, HttpsURLConnection con) throws IOException {
        RestResponse<T> rsp = new RestResponse<>();
        rsp.setCode(con.getResponseCode());

        BufferedReader input = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String msg = input.lines().collect(Collectors.joining());

        if (rsp.getCode() == 200) {
            rsp.setData(mapper.readValue(msg, rspClass));
        } else {
            rsp.setError(msg);
        }

        return rsp;
    }
}
