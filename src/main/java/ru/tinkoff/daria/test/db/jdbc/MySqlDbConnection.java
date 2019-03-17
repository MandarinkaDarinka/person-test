package ru.tinkoff.daria.test.db.jdbc;

import java.sql.*;

public class MySqlDbConnection {

    private final DBConfig config;
    private Connection connection;

    public MySqlDbConnection(DBConfig config) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        this.config = config;
    }

    public Connection create() {
        try {
            if (connection == null || !connection.isValid(5))
            {
                connection = DriverManager.getConnection(config.getUrl(), config.getUsername(), config.getPassword());
                return connection;
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer execUpdate(String sqlFormat, Object... args) {
        String sql = String.format(sqlFormat, args);
        System.out.println(sql);

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()){
                return rs.getInt(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String exec(String sqlFormat, String field, Object... args) {
        try {
            ResultSet resultSet = execResultSet(sqlFormat, args);
            if (resultSet.next()) {
                return resultSet.getString(field);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet execResultSet(String sqlFormat, Object... args) throws SQLException {
        String sql = String.format(sqlFormat, args);
        System.out.println(sql);

        return create().createStatement().executeQuery(sql);
    }
}
