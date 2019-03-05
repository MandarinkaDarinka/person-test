package ru.tinkoff.daria.test.localdataprovider.factory;

import ru.tinkoff.daria.test.localdataprovider.reader.DataReader;
import ru.tinkoff.daria.test.model.PersonDto;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.tinkoff.daria.test.common.TestConsts.*;
import static ru.tinkoff.daria.test.utils.InnService.*;

public class LocalDataPersonFactory {

    private final DataReader birthDateReader;
    private final DataReader cityReader;
    private final DataReader countryReader;
    private final DataReader firstNameFemaleReader;
    private final DataReader firstNameMaleReader;
    private final DataReader lastNameFemaleReader;
    private final DataReader lastNameMaleReader;
    private final DataReader patronymicFemaleReader;
    private final DataReader patronymicMaleReader;
    private final DataReader regionReader;
    private final DataReader streetReader;

    public LocalDataPersonFactory() {
        birthDateReader = new DataReader(BIRTH_DATE_PATH);
        cityReader = new DataReader(CITY_PATH);
        countryReader = new DataReader(COUNTRY_PATH);
        firstNameFemaleReader = new DataReader(FIRST_NAME_FEMALE_PATH);
        firstNameMaleReader = new DataReader(FIRST_NAME_MALE_PATH);
        lastNameFemaleReader = new DataReader(LAST_NAME_FEMALE_PATH);
        lastNameMaleReader = new DataReader(LAST_NAME_MALE_PATH);
        patronymicFemaleReader = new DataReader(PATRONYMIC_FEMALE_PATH);
        patronymicMaleReader = new DataReader(PATRONYMIC_MALE_PATH);
        regionReader = new DataReader(REGION_PATH);
        streetReader = new DataReader(STREET_PATH);
    }

    public PersonDto getPerson() throws ParseException {
        PersonDto p = new PersonDto();
        p.setBirthDate(LocalDate.parse(birthDateReader.getRandom(), DATE_FORMAT));
        p.setRegion(regionReader.getRandom());
        p.setStreet(streetReader.getRandom());
        p.setCountry(countryReader.getRandom());
        p.setCity(cityReader.getRandom());

        Random rnd = new Random();
        boolean sex = rnd.nextBoolean();

        if(!sex) {
            p.setFirstName(firstNameMaleReader.getRandom());
            p.setLastName(lastNameMaleReader.getRandom());
            p.setPatronymic(patronymicMaleReader.getRandom());
            p.setSex(sex);
        } else {
            p.setFirstName(firstNameFemaleReader.getRandom());
            p.setLastName(lastNameFemaleReader.getRandom());
            p.setPatronymic(patronymicFemaleReader.getRandom());
            p.setSex(sex);
        }

        p.setInn(getIndividualInn());
        p.setPostcode(String.valueOf(getPostcode()));

        p.setHouse(String.valueOf(rnd.nextInt(49) + 1));
        p.setApartments(String.valueOf(rnd.nextInt(199) + 1));

        return p;
    }

    public List<PersonDto> getPersonList(int quantity) throws ParseException {
        List<PersonDto> persons = new ArrayList<>();

        for (int i = 0; i < quantity; i++)
            persons.add(getPerson());

        return persons;
    }

    private int getPostcode()//Генерация Индекса
    {
        Random rnd = new Random();
        // Получаем случайное число в диапазоне от min до max (включительно)
        return 100000 + rnd.nextInt(200000 - 100001 + 1);
    }
}
