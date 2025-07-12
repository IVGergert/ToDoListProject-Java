package com.todolist.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.todolist.Model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EditTaskController  {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button EditTask;

    @FXML
    private TextField NewTaskNameField;

    private Task task;
    private Consumer<Task> onSaveHandler;

    public void setDate(Task task, Consumer<Task> onSaveHandler) {
        this.task = task;
        this.onSaveHandler = onSaveHandler;
        NewTaskNameField.setText(task.getTitle());
    }

    @FXML
    void EditTaskHandle(ActionEvent event) {
        String newTitle = NewTaskNameField.getText().trim();

        if (newTitle.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,"Ошибка", "Поле не может быть пустым!");
            return;
        }

        if (!newTitle.equals(task.getTitle())) {
            task.setTitle(newTitle);
            if (onSaveHandler != null) {
                onSaveHandler.accept(task);
            }
        }

        EditTask.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
