package ru.tinkoff.daria.test.localdataprovider.reader;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public class DataReader {

    private final BufferedReader bufferedReader;
    private final List<String> dataList;

    public DataReader(String filePath) {
        this.bufferedReader = getReader(filePath);
        this.dataList = getDataList();
    }

    private BufferedReader getReader(String filePath) {
        Charset inputCharset = Charset.forName("UTF-8");
        InputStream file = DataReader.class.getResourceAsStream(filePath);
        InputStreamReader fr = new InputStreamReader(file, inputCharset);
        return new BufferedReader(fr);
    }

    private List<String> getDataList() {
        return bufferedReader.lines().collect(Collectors.toList());
    }

    public String getRandom() {
        Random rnd = new Random();
        return dataList.get(rnd.nextInt(dataList.size()));
    }
}
