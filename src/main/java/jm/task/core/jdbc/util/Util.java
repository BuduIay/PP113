package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/555";
    private static final String USER = "root";
    private static final String PASSWORD = "Root";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Соединение с БД успешно установлено!");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД: ");
        }
        return connection;
    }
}

