package com.geekbrains.netty;

import java.sql.*;

public class DataBaseQuery {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatementRegistry;


    public static boolean checkLogin(String login, String password) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT login, password FROM users;");

        while (rs.next()) {
            if (login.equals(rs.getString("login")) && password.equals(rs.getString("password"))) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public static synchronized boolean isRegistered(String login) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT login FROM users;");

        while (rs.next()) {
            if (login.equals(rs.getString("login"))) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public static boolean addNewUser(String login, String password) throws SQLException {
        if (!isRegistered(login)) {
            preparedStatementRegistry.setString(1, login);
            preparedStatementRegistry.setString(2, password);
            preparedStatementRegistry.executeUpdate();
//            statement.executeUpdate(String.format("UPDATE users SET name = '%s' WHERE name = '%s'", updateName, name));

            return true;
        }

        return false;
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::resource:com/geekbrains/cloud.db");
        statement = connection.createStatement();
        preparedStatementRegistry = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
        preparedStatementRegistry = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
    }

    public static void disconnect() {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (preparedStatementRegistry != null) {
                preparedStatementRegistry.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}


