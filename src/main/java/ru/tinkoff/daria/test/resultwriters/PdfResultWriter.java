package ru.tinkoff.daria.test.resultwriters;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import org.apache.poi.util.IOUtils;
import ru.tinkoff.daria.test.model.PersonDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ru.tinkoff.daria.test.common.TestConsts.*;

public class PdfResultWriter {

    private final FileOutputStream resultFile;
    private final Document document;
    private final Table table;
    private final PdfFont font;

    private final List<String> headers = Arrays.asList(
            "Имя", "Фамилия", "Отчество", "Возраст", "Пол", "Дата рождения", "ИНН",
            "Почтовый индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира");

    public PdfResultWriter() {
        this.resultFile = getResultFile();
        PdfWriter writer = new PdfWriter(resultFile);
        PdfDocument pdf = new PdfDocument(writer);
        this.document = new Document(pdf, new PageSize(1080, 1080));

        try {
            font = getFont();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        this.table = new Table(headers.size());

        setHeader();
    }

    private FileOutputStream getResultFile()  {
        try {
            File f = new File(PDF_RESULT_FILE);
            System.out.println("Pdf file would be saved: " + f.getCanonicalPath());
            return new FileOutputStream(f);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void setHeader() {
        headers.forEach(columnTitle -> {
            Cell header = new Cell();
            header.setBackgroundColor(Color.LIGHT_GRAY); //Заливка ячеек серым цветом
            header.add(getEncodedCell(columnTitle));
            table.addCell(header);
        });
    }

    private Cell getEncodedCell(String text) {
        return new Cell().setFont(font).setFontSize(12).add(text);
    }

    private PdfFont getFont() throws IOException {
        byte[] fontContents = IOUtils.toByteArray(getClass().getResourceAsStream(FONT));
        FontProgram fontProgram = FontProgramFactory.createFont(fontContents, true);
        return PdfFontFactory.createFont(fontProgram, "CP1251", true);
    }

    public void addRow(PersonDto p) {
        this.table.addCell(getEncodedCell(p.getFirstName()));
        this.table.addCell(getEncodedCell(p.getLastName()));
        this.table.addCell(getEncodedCell(p.getPatronymic()));
        this.table.addCell(getEncodedCell(String.valueOf(p.getAge())));
        this.table.addCell(getEncodedCell(p.isSex() ? "Ж" : "М"));
        this.table.addCell(getEncodedCell(DATE_FORMAT.format(p.getBirthDate())));
        this.table.addCell(getEncodedCell(p.getInn()));
        this.table.addCell(getEncodedCell(String.valueOf(p.getPostcode())));
        this.table.addCell(getEncodedCell(p.getCountry()));
        this.table.addCell(getEncodedCell(p.getRegion()));
        this.table.addCell(getEncodedCell(p.getCity()));
        this.table.addCell(getEncodedCell(p.getStreet()));
        this.table.addCell(getEncodedCell(p.getHouse()));
        this.table.addCell(getEncodedCell(p.getApartments()));
    }

    public void addRows(List<PersonDto> pp) throws IOException {
        pp.forEach(this::addRow);

        Paragraph p = new Paragraph();
        this.document.add(table);
        this.document.close();
        resultFile.close();
    }

}
