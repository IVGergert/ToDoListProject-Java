package com.todolist.DB;

import com.todolist.Model.User;

import java.sql.*;

public class UserDB {
    private static final String URL = "jdbc:mysql://localhost:3306/to-do-list";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException exception) {
            System.out.println("Ошибка подключения к БД: " + exception.getMessage());
            return null;
        }
    }

    public User getUserFromDB(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void addNewUser(User user){
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        Connection connection = getConnection();
        if (connection == null) {
            return;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getUserByUsername(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ? LIMIT 1";
        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()){
                return rs.next();
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new RuntimeException("Ошибка проверки имени пользователя", exception);
        }
    }

    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ? LIMIT 1";
        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public User getUserByUsernameAndEmail(String username, String email) {
        String sql = "SELECT * FROM users WHERE username = ? AND email = ?";
        Connection connection = getConnection();
        if (connection == null) return null;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePassword(User user){
        String sql = "UPDATE users SET password = ? WHERE email = ?";
        Connection conn = getConnection();
        if (conn == null) {
            return;
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());

            ps.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
