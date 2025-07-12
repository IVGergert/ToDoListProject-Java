
package com.todolist.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.todolist.DB.UserDB;
import com.todolist.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private TextField UsernameField;

    private UserDB userDB;

    @FXML
    void ForgetPasswordHandle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/ResetPasswordView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = new Stage();
            stage.setTitle("Восстановления пароля");
            stage.setScene(scene);
            stage.show();

            UsernameField.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Ошибка открытия окна восстановления пароля","Ошибка при открытии окна восстановления пароля.");
        }
    }

    @FXML
    void LoginHandle() {
        String username = UsernameField.getText().trim();
        String password = PasswordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Пустые поля","Введите имя пользователя или пароль.");
            return;
        }

        User user = userDB.getUserFromDB(username,password);

        if (user != null) {
            openToDoListApp(user);
        } else {
            showAlert(Alert.AlertType.ERROR,"Ошибка авторизации","Неверный логин или пароль.");
        }
    }

    @FXML
    void RegisterHandle(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/RegisterView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = new Stage();
            stage.setTitle("Список задач");
            stage.setScene(scene);
            stage.show();

            UsernameField.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Ошибка открытия окна регистрации","Ошибка при открытии окна регистрации.");
        }
    }

    @FXML
    void initialize() {
        userDB = new UserDB();
    }

    private void openToDoListApp(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/ToDoListView.fxml"));
            Scene scene = new Scene(loader.load(), 600, 550);

            ToDoListController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Список задач");
            stage.setScene(scene);
            stage.show();

            UsernameField.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR,"Ошибка открытия списка задач","Ошибка при открытии списка задач.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
