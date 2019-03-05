package ru.tinkoff.daria.test.restdataprovider.restclient;

import ru.tinkoff.daria.test.restdataprovider.model.GetPersonsResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.tinkoff.daria.test.common.TestConsts.BASE_PATH;
import static ru.tinkoff.daria.test.common.TestConsts.ENDPOINT_URL;

public class RestApi {
    private final RestClient restClient;
    private final int LIMIT = 10;

    public RestApi(String url) throws IOException {
        this.restClient = new RestClient(url);
    }

    public RestResponse<GetPersonsResponse> getPersons(int quantity) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("nat", "us");
        params.put("results", String.valueOf(quantity));

        return restClient.get(BASE_PATH, GetPersonsResponse.class, params);
    }
}
