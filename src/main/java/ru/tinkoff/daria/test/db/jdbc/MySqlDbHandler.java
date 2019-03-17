package ru.tinkoff.daria.test.db.jdbc;

import ru.tinkoff.daria.test.model.PersonDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.tinkoff.daria.test.common.TestConsts.SQL_DATE_FORMAT;

public class MySqlDbHandler {

    private final MySqlDbConnection connection;

    public MySqlDbHandler(DBConfig config) {
        this.connection = new MySqlDbConnection(config);
    }

    public boolean isPresent(String firstName, String lastName, String patronymic) throws SQLException {
        ResultSet result = connection.execResultSet("SELECT * FROM persons WHERE name='%s' " +
                "AND surname='%s' AND middlename='%s'", firstName, lastName, patronymic);

        if (result.next())
            return true;

        return false;
    }

    public void upsertPerson(PersonDto person) throws SQLException {
        if (isPresent(person.getFirstName(), person.getLastName(), person.getPatronymic())) {
            Integer id = Integer.parseInt(connection.exec("SELECT * FROM persons WHERE name='%s' AND surname='%s' AND middlename='%s'",
                    "address_id", person.getFirstName(), person.getLastName(), person.getPatronymic()));

            connection.execUpdate("UPDATE persons " +
                    "SET birthday = '%s', gender='%s', inn='%s' " +
                    "WHERE name='%s' AND surname='%s' AND middlename='%s'",
                    SQL_DATE_FORMAT.format(person.getBirthDate()), person.isSex() ? "Ж" : "М", person.getInn(),
                    person.getFirstName(), person.getLastName(), person.getPatronymic());

            connection.execUpdate("UPDATE address " +
                    "SET postcode = '%s', country='%s', region='%s', city='%s', street ='%s', house=%s, flat=%s " +
                    "WHERE id=%s",
                    person.getPostcode(), person.getCountry(), person.getRegion(), person.getCity(), person.getStreet(),
                    person.getHouse(), person.getApartments(), id);
        } else {
            int addressId = connection.execUpdate("INSERT INTO address " +
                    "(postcode, country, region, city, street, house, flat) " +
                    "VALUES ('%s', '%s', '%s', '%s', '%s', %s, %s)",
                    person.getPostcode(), person.getCountry(), person.getRegion(), person.getCity(), person.getStreet(),
                    person.getHouse(), person.getApartments());

            connection.execUpdate("INSERT INTO persons " +
                    "(name, surname, middlename, birthday, gender, inn, address_id) " +
                    "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %s)",
                    person.getFirstName(), person.getLastName(), person.getPatronymic(),
                    SQL_DATE_FORMAT.format(person.getBirthDate()),
                    person.isSex() ? "Ж" : "М", person.getInn(), addressId);
        }
    }

    public void upsertPersons(List<PersonDto> persons) throws SQLException {
        for (PersonDto person : persons) {
            this.upsertPerson(person);
        }
    }

    public List<PersonDto> getPersonList(int quantity) throws SQLException {
        List<PersonDto> persons = new ArrayList<>();

        ResultSet personsResult = connection.execResultSet("SELECT * FROM persons LIMIT %s", quantity);

        while (personsResult.next()) {
            PersonDto person = new PersonDto();

            String address_id = personsResult.getString("address_id");

            ResultSet address = connection.execResultSet("SELECT * FROM address WHERE id=%s", address_id);

            if (address.next()) {
                person.setPostcode(address.getString("postcode"));
                person.setCountry(address.getString("country"));
                person.setRegion(address.getString("region"));
                person.setCity(address.getString("city"));
                person.setStreet(address.getString("street"));
                person.setHouse(address.getString("house"));
                person.setApartments(address.getString("flat"));
            } else {
                throw new RuntimeException("Can't retrieve address info.");
            }

            person.setFirstName(personsResult.getString("name"));
            person.setLastName(personsResult.getString("surname"));
            person.setPatronymic(personsResult.getString("middlename"));
            person.setBirthDate(LocalDate.parse(personsResult.getString("birthday"), SQL_DATE_FORMAT));

            if (personsResult.getString("gender").equals("Ж"))
                person.setSex(true);
            else
                person.setSex(false);

            person.setInn(personsResult.getString("inn"));

            persons.add(person);
        }

        return persons;
    }
}
