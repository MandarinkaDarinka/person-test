package ru.tinkoff.daria.test.restdataprovider.factory;

import ru.tinkoff.daria.test.model.PersonDto;
import ru.tinkoff.daria.test.restdataprovider.model.GetPersonsResponse;
import ru.tinkoff.daria.test.restdataprovider.model.Person;
import ru.tinkoff.daria.test.restdataprovider.restclient.RestApi;
import ru.tinkoff.daria.test.restdataprovider.restclient.RestResponse;
import ru.tinkoff.daria.test.utils.InnService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.tinkoff.daria.test.common.TestConsts.ENDPOINT_URL;

public class RestDataFactory {

    private final RestApi restApi;

    public RestDataFactory() throws IOException {
        this.restApi = new RestApi(ENDPOINT_URL);
    }

    public PersonDto getPerson() throws IOException {
        RestResponse<GetPersonsResponse> pp = restApi.getPersons(1);

        if (pp.getCode() == 200) {
            return mapResponseToPerson(pp.getData().getResults().get(0));
        } else {
            throw new IOException("Response code: " + pp.getCode());
        }
    }

    public List<PersonDto> getPersons (int quantity) throws IOException {
        RestResponse<GetPersonsResponse> pp = restApi.getPersons(quantity);

        if (pp.getCode() == 200) {
            List<PersonDto> personDtos = new ArrayList<>();

            pp.getData().getResults().forEach(s -> personDtos.add(mapResponseToPerson(s)));

            return personDtos;
        } else {
            throw new IOException("Response code: " + pp.getCode());
        }
    }

    private PersonDto mapResponseToPerson(Person p) {
        PersonDto pDto = new PersonDto();

        Random rnd = new Random();

        pDto.setFirstName(p.getName().getFirst());
        pDto.setLastName(p.getName().getLast());
        pDto.setPatronymic("");
        pDto.setAge(p.getDob().getAge());
        pDto.setBirthDate(p.getDob().getDate().toLocalDate());
        pDto.setCountry(p.getNat());
        pDto.setCity(p.getLocation().getCity());
        pDto.setRegion(p.getLocation().getState());
        pDto.setStreet(p.getLocation().getStreet());
        pDto.setHouse(String.valueOf(rnd.nextInt(49) + 1));
        pDto.setApartments(String.valueOf(rnd.nextInt(199) + 1));
        pDto.setInn(InnService.getIndividualInn());

        if (p.getGender().equals("female"))
            pDto.setSex(true);
        else
            pDto.setSex(false);

        pDto.setPostcode(p.getLocation().getPostcode());

        return pDto;
    }
}
