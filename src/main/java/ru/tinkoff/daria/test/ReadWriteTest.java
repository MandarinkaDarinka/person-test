package ru.tinkoff.daria.test;

import ru.tinkoff.daria.test.db.jdbc.DBConfig;
import ru.tinkoff.daria.test.db.jdbc.MySqlDbHandler;
import ru.tinkoff.daria.test.db.jdbc.PropsLoader;
import ru.tinkoff.daria.test.localdataprovider.factory.LocalDataPersonFactory;
import ru.tinkoff.daria.test.model.PersonDto;
import ru.tinkoff.daria.test.restdataprovider.factory.RestDataFactory;
import ru.tinkoff.daria.test.resultwriters.PdfResultWriter;
import ru.tinkoff.daria.test.resultwriters.XmlResultWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static ru.tinkoff.daria.test.common.TestConsts.DB_PROPS_PATH;

public class ReadWriteTest {

    private static LocalDataPersonFactory personFactory = new LocalDataPersonFactory();
    private static XmlResultWriter xmlResultWriter = new XmlResultWriter();
    private static PdfResultWriter pdfResultWriter = new PdfResultWriter();

    public static void main(String[] args) throws SQLException, ParseException, IOException {

        PropsLoader loader = new PropsLoader(DB_PROPS_PATH);
        DBConfig config = loader.getDbConfig();
        MySqlDbHandler handler = new MySqlDbHandler(config);

        List<PersonDto> persons = null;

        try {
            RestDataFactory restDataFactory = new RestDataFactory();
            persons = restDataFactory.getPersonList(30);
            handler.upsertPersons(persons);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (persons == null || persons.size() == 0) {
            persons = handler.getPersonList(30);
        }

        if (persons == null || persons.size() == 0) {
            persons = personFactory.getPersonList(30);
        }

        xmlResultWriter.addRows(persons);
        pdfResultWriter.addRows(persons);
    }


}
