package com.todolist.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import com.todolist.DB.TaskDB;
import com.todolist.Model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TaskItemController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView EditTaskButton;

    @FXML
    private CheckBox CheckBox;

    @FXML
    private Label titleLabel;

    private Task task;
    private TaskDB taskDB;
    private Runnable onDeleteCallback;
    private Consumer<Task> onUpdateCallback;

    public void setOnUpdateCallback(Consumer<Task> callback) {
        this.onUpdateCallback = callback;
    }

    public void setTask(Task task, Runnable onDelete){
        this.task = task;
        this.onDeleteCallback = onDelete;
        updateUI();
    }

    private void updateUI() {
        if (task != null) {
            titleLabel.setText(task.getTitle());
            CheckBox.setSelected(task.isDone());
        }
    }

    @FXML
    void DeleteTaskHandle(MouseEvent event) {
        if (taskDB.deleteTask(task.getId())) {
            onDeleteCallback.run();
        }
    }

    @FXML
    void EditTaskHandle(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/EditTask.fxml"));
            Scene scene = new Scene(loader.load(), 500, 300);

            EditTaskController editTaskController = loader.getController();
            editTaskController.setDate(task, updatedTask -> {
                if (taskDB.updateTask(updatedTask)) {
                    titleLabel.setText(updatedTask.getTitle());

                    if (onUpdateCallback != null) {
                        onUpdateCallback.accept(updatedTask);
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось сохранить изменения!");
                }
            });

            Stage stage = new Stage();
            stage.initOwner(EditTaskButton.getScene().getWindow());
            stage.setTitle("Редактирование задачи");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Ошибка открытия окна редактирования","Ошибка при открытии окна редактирования.");
        }
    }

    @FXML
    void initialize() {
        taskDB = new TaskDB();

        CheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (task != null) {
                task.setDone(newVal);

                if (taskDB.updateTask(task)) {
                    if (onUpdateCallback != null) {
                        onUpdateCallback.accept(task);
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Ошибка", "Не удалось сохранить статус задачи!");
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
