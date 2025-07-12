package com.todolist.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.todolist.DB.TaskDB;
import com.todolist.Model.Task;
import com.todolist.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class ToDoListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField TaskInputField;

    @FXML
    private ListView<AnchorPane> taskListView;

    private TaskDB taskDB;
    private User user;
    private ObservableList<Task> allTasks = FXCollections.observableArrayList();
    private ObservableList<Task> filteredTasks = FXCollections.observableArrayList();

    public void setUser(User user) {
        this.user = user;
        loadTasks();
    }

    @FXML
    void AddTaskHandle(ActionEvent event) {
        String text = TaskInputField.getText().trim();

        if (text.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Пустая задача", "Введите текст задачи.");
            return;
        }

        Task task = new Task(0, user.getId(), text, false);

        if (taskDB.addTask(task)) {
            task.setId(taskDB.getLastInsertId());
            allTasks.add(task);
            applyFilter();
            TaskInputField.clear();
        } else {
            showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось добавить задачу");
        }
    }

    private void loadTasks() {
        if (user != null) {
            allTasks.setAll(taskDB.getTasksByUserId(user.getId()));
            applyFilter();
        }
    }

    private void addTaskToView(Task task) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/TaskItem.fxml"));
            AnchorPane taskPane = loader.load();

            TaskItemController taskItemController = loader.getController();

            taskItemController.setTask(task, () -> {
                allTasks.remove(task);
                applyFilter();
            });

            taskItemController.setOnUpdateCallback(updatedTask -> {
                allTasks.setAll(taskDB.getTasksByUserId(user.getId()));
                applyFilter();
            });

            taskListView.getItems().add(taskPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyFilter() {
        String selectedFilter = filterComboBox.getValue();
        filteredTasks.setAll(allTasks);

        if (selectedFilter == null || selectedFilter.equals("Все задачи")) {
            filteredTasks.setAll(allTasks);
        } else if (selectedFilter.equals("Только активные")) {
            filteredTasks.setAll(allTasks.filtered(task -> !task.isDone()));
        } else if (selectedFilter.equals("Только выполненные")) {
            filteredTasks.setAll(allTasks.filtered(Task::isDone));
        }

        taskListView.getItems().clear();
        for (Task task : filteredTasks) {
            addTaskToView(task);
        }
    }

    @FXML
    void initialize() {
        taskDB = new TaskDB();

        loadTasks();

        filterComboBox.getItems().addAll(
                "Все задачи",
                "Только активные",
                "Только выполненные"
        );

        filterComboBox.setValue("Все задачи");
        filterComboBox.setOnAction(e -> applyFilter());
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}