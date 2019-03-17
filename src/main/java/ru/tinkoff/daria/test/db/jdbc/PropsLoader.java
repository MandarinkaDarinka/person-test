package ru.tinkoff.daria.test.db.jdbc;

import java.io.*;

public class PropsLoader {

    private final String propsPath;

    public PropsLoader(String propsPath) {
        this.propsPath = propsPath;
    }

    public DBConfig getDbConfig() {
        DBConfig config = new DBConfig();

        try (BufferedReader br = new BufferedReader(new FileReader(new File(propsPath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] prop = line.split("=", 2);

                if (prop.length != 2)
                    throw new RuntimeException("Invalid property file");

                switch (prop[0]) {
                    case "url": {
                        config.setUrl(prop[1]);
                        break;
                    }
                    case "username": {
                        config.setUsername(prop[1]);
                        break;
                    }
                    case "password": {
                        config.setPassword(prop[1]);
                        break;
                    }
                    default: {
                        throw new RuntimeException("Unknown property");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read from file: " + propsPath);
            throw new RuntimeException(e);
        }

        return config;
    }
}
