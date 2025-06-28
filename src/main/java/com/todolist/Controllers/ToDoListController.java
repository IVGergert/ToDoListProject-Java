package com.todolist.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class ToDoListController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private ListView<String> taskListView;

    @FXML
    void AddTaskHandle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/AddTask.fxml"));
            Scene scene = new Scene(loader.load(), 300, 300);

//             TodoListController controller = loader.getController();
//             controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Список задач");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка открытия окна","Ошибка при открытии окна добавления.");
        }
    }

    @FXML
    void initialize() {
        filterComboBox.getItems().addAll(
                "Все задачи",
                "Только активные",
                "Только выполненные"
        );
        filterComboBox.setValue("Все задачи");
    }

    public void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
