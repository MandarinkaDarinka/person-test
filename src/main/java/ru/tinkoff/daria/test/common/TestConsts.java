package ru.tinkoff.daria.test.common;

import java.time.format.DateTimeFormatter;

public class TestConsts {
    public static final String BIRTH_DATE_PATH = "/birthDate.txt";
    public static final String CITY_PATH = "/city.txt";
    public static final String COUNTRY_PATH = "/country.txt";
    public static final String FIRST_NAME_FEMALE_PATH = "/firstNameFemale.txt";
    public static final String FIRST_NAME_MALE_PATH = "/firstNameMale.txt";
    public static final String LAST_NAME_FEMALE_PATH = "/lastNameFemale.txt";
    public static final String LAST_NAME_MALE_PATH = "/lastNameMale.txt";
    public static final String PATRONYMIC_FEMALE_PATH = "/patronymicFemale.txt";
    public static final String PATRONYMIC_MALE_PATH = "/patronymicMale.txt";
    public static final String REGION_PATH = "/region.txt";
    public static final String STREET_PATH = "/street.txt";
    public static final String XLS_RESULT_FILE = "result.xls";
    public static final String PDF_RESULT_FILE = "result.pdf";
    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final String FONT = "/freesans.ttf";
    public static final String ENDPOINT_URL = "https://randomuser.me";
    public static final String BASE_PATH = "/api";
}
