package com.todolist.DB;

import com.todolist.Model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDB {
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

    public List<Task> getTasksByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE userId = ?";

        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("title"),
                        rs.getBoolean("is_done")
                );
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    public boolean addTask(Task task) {
        String sql = "INSERT INTO tasks (userId, title, is_done) VALUES (?, ?, ?)";

        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, task.getUserId());
            ps.setString(2, task.getTitle());
            ps.setBoolean(3, task.isDone());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateTask(Task task) {
        String sql = "UPDATE tasks SET title = ?, is_done = ? WHERE id = ?";

        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, task.getTitle());
            ps.setBoolean(2, task.isDone());
            ps.setInt(3, task.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка при обновлении задачи: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        Connection connection = getConnection();
        if (connection == null) {
            return false;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Ошибка при удалении задачи: " + e.getMessage());
            return false;
        }
    }

    public int getLastInsertId() {
        String sql = "SELECT * FROM tasks ORDER BY id DESC LIMIT 1";

        Connection connection = getConnection();
        if (connection == null) {
            return 0;
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении последнего ID: " + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }
}
