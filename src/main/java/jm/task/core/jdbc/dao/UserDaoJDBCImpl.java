package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT" +
                    ", name VARCHAR(100)," +
                    " lastname VARCHAR(50)" +
                    ", age INT)");
            System.out.println("Table created");
        } catch (SQLException e) {
            System.out.println("Table not created");
        }

    }


    public void dropUsersTable() {
        try (Connection connection = Util.connect();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            System.out.println("Table not dropped");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.connect();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users " +
                     "(name, lastName, age) " +
                     "VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("User not saved");
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.connect();
             PreparedStatement prepStat = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            prepStat.setLong(1, id);
            prepStat.executeUpdate();
        } catch (SQLException e) {
            System.out.println("User not removed");

        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new LinkedList<>();
        try (Connection connection = Util.connect();
             Statement preparedstatment = connection.createStatement();
             ResultSet resultSet = preparedstatment.executeQuery("SELECT * " + " FROM users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println("User not loaded");
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.connect();
             Statement preparedstatment = connection.createStatement()) {
            preparedstatment.executeUpdate("TRUNCATE users");
        } catch (SQLException e) {
            System.out.println("Table not cleaned");
        }
    }

}
