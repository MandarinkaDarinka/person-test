package ru.tinkoff.daria.test.resultwriters;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.tinkoff.daria.test.model.PersonDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static ru.tinkoff.daria.test.common.TestConsts.DATE_FORMAT;
import static ru.tinkoff.daria.test.common.TestConsts.XLS_RESULT_FILE;

public class XmlResultWriter {

    private final FileOutputStream resultFile;
    private final Workbook book;
    private final Sheet sheet;
    private final List<String> headers = Arrays.asList(
            "Имя", "Фамилия", "Отчество", "Возраст", "Пол", "Дата рождения", "ИНН",
            "Почтовый индекс", "Страна", "Область", "Город", "Улица", "Дом", "Квартира");

    public XmlResultWriter() {
        this.resultFile = getResultFile();
        this.book = new HSSFWorkbook();
        this.sheet = book.createSheet("Data");
        setHeader();
    }

    private FileOutputStream getResultFile()  {
        try {
            File f = new File(XLS_RESULT_FILE);
            System.out.println("Xls file would be saved: " + f.getCanonicalPath());
            return new FileOutputStream(f);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void setHeader() {
        Row row = sheet.createRow(sheet.getLastRowNum());

        for (int i = 0; i < headers.size(); i++) {
            fillExcelCell(headers.get(i), row, i);
        }
    }

    private <T> int fillExcelCell(T rec, Row row, int cellNum) {
        Cell cell = row.createCell(cellNum++); //cell - ячейка новая
        cell.setCellValue(String.valueOf(rec));
        return cellNum;
    }

    public void addRow(PersonDto p) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        int cellNum = 0;
        cellNum = fillExcelCell(p.getFirstName(), row, cellNum);
        cellNum = fillExcelCell(p.getLastName(), row, cellNum);
        cellNum = fillExcelCell(p.getPatronymic(), row, cellNum);
        cellNum = fillExcelCell(p.getAge(), row, cellNum);
        cellNum = fillExcelCell(p.isSex() ? "Ж" : "М", row, cellNum);
        cellNum = fillExcelCell(DATE_FORMAT.format(p.getBirthDate()), row, cellNum);
        cellNum = fillExcelCell(p.getInn(), row, cellNum);
        cellNum = fillExcelCell(p.getPostcode(), row, cellNum);
        cellNum = fillExcelCell(p.getCountry(), row, cellNum);
        cellNum = fillExcelCell(p.getRegion(), row, cellNum);
        cellNum = fillExcelCell(p.getCity(), row, cellNum);
        cellNum = fillExcelCell(p.getStreet(), row, cellNum);
        cellNum = fillExcelCell(p.getHouse(), row, cellNum);
        fillExcelCell(p.getApartments(), row, cellNum);
    }

    public void addRows(List<PersonDto> pp) throws IOException {
        pp.forEach(this::addRow);

        this.book.write(resultFile);
        this.book.close();
        resultFile.close();
    }
}
