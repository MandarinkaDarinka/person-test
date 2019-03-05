package ru.tinkoff.daria.test;

import ru.tinkoff.daria.test.localdataprovider.factory.LocalDataPersonFactory;
import ru.tinkoff.daria.test.model.PersonDto;
import ru.tinkoff.daria.test.restdataprovider.factory.RestDataFactory;
import ru.tinkoff.daria.test.resultwriters.PdfResultWriter;
import ru.tinkoff.daria.test.resultwriters.XmlResultWriter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ReadWriteTest {

    private static LocalDataPersonFactory personFactory = new LocalDataPersonFactory();
    private static XmlResultWriter xmlResultWriter = new XmlResultWriter();
    private static PdfResultWriter pdfResultWriter = new PdfResultWriter();

    public static void main(String[] args) throws ParseException, IOException {

        List<PersonDto> persons = null;

        try {
            RestDataFactory restDataFactory = new RestDataFactory();
            persons = restDataFactory.getPersons(30);

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (persons == null)
            persons = personFactory.getPersonList(30);

        xmlResultWriter.addRows(persons);
        pdfResultWriter.addRows(persons);
    }
}
