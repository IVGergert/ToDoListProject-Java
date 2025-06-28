package com.todolist.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.todolist.DB.UserDB;
import com.todolist.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text ForgetPasswordButton;

    @FXML
    private Button LoginButton;

    @FXML
    private TextField PasswordField;

    @FXML
    private Button RegisterButton;

    @FXML
    private TextField UsernameField;

    private UserDB userDB;

    @FXML
    void ForgetPassword(MouseEvent event) {

    }

    @FXML
    void LoginHandle(ActionEvent event) {
        String username = UsernameField.getText().trim();
        String password = PasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()){
            showErrorAlert("Пустые поля","Введите имя пользователя или пароль.");
            return;
        }

        User user = userDB.getUserFromDB(username,password);

        if (user != null) {
            openToDoListApp(user);
        } else {
            showErrorAlert("Ошибка авторизации","Неверный логин или пароль.");
        }
    }

    @FXML
    void RegisterHandle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/RegisterView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

//             TodoListController controller = loader.getController();
//             controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Список задач");
            stage.setScene(scene);
            stage.show();

            UsernameField.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка открытия ToDoList","Ошибка при открытии списка задач.");
        }
    }

    @FXML
    void initialize() {
        userDB = new UserDB();
    }

    private void openToDoListApp(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/ToDoListView.fxml"));
            Scene scene = new Scene(loader.load(), 300, 300);

//             TodoListController controller = loader.getController();
//             controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Список задач");
            stage.setScene(scene);
            stage.show();

            UsernameField.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка открытия ToDoList","Ошибка при открытии списка задач.");
        }
    }

    public void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
