package com.geekbrains.chat.server;

import java.sql.*;

public class UsersRegistry {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    public UsersRegistry() throws SQLException {
        connect();
    }

    public static synchronized boolean isUserRegistered(String name) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT name FROM users;");

        while (rs.next()) {
            if (name.equals(rs.getString("name"))) {
                rs.close();
                return true;
            }
        }
        rs.close();
        return false;
    }

    public static boolean setUpdateUsername(String name, String updateName) throws SQLException {
        if (!isUserRegistered(updateName)) {
            preparedStatement.setString(1, updateName);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
//            statement.executeUpdate(String.format("UPDATE users SET name = '%s' WHERE name = '%s'", updateName, name));

            return true;
        }

        return false;
    }

    public static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:main.db");
        statement = connection.createStatement();
        preparedStatement = connection.prepareStatement("UPDATE users SET name = ? WHERE name = ?");
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
            if (preparedStatement != null) {
                preparedStatement.close();
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
