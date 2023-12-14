package service;

import model.User;

import java.sql.*;

public class Database {
    private static Connection connection;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    public Database() throws SQLException {
        initialize();
    }

    public static void initialize() {
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL,USER,PASS);

            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS SUSERS (USER_ID INT PRIMARY KEY, USER_GUID VARCHAR(255), USER_NAME VARCHAR(255))");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }

    }

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getUserGuid());
            statement.setString(3, user.getUserName());
            statement.executeUpdate();
        }

    }

    public void printAllUsers() throws SQLException {
        String sql = "SELECT * FROM SUSERS";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userGuid = resultSet.getString("user_guid");
                String userName = resultSet.getString("user_name");
                System.out.println("User ID: " + userId + ", User guid: " + userGuid + ", User name: " + userName);
            }

            resultSet.close();
        }
    }

    public void deleteAllUsers() throws SQLException {
        String sql = "DELETE FROM SUSERS";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM SUSERS WHERE USER_ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.executeUpdate();
        }
    }

    public User getUser(int userId) throws SQLException {
        String sql = "SELECT * FROM SUSERS WHERE USER_ID = ?";
        User user = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String userGuid = resultSet.getString("user_guid");
                String userName = resultSet.getString("user_name");
                user = new User(userId, userGuid, userName);
            }
            resultSet.close();
        }

        return user;
    }

    public Connection getConnection() {
        return connection;
    }
}