package com.todolist.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.todolist.DB.UserDB;
import com.todolist.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField EmailField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button RegisterButton;

    @FXML
    private PasswordField RepeatPasswordField;

    @FXML
    private TextField UsernameField;

    private UserDB userDB;

    @FXML
    void RegisterHandle() {
        if (AddNewAccount()){
            openLoginWindow();
        }
    }

    @FXML
    void initialize() {
        userDB = new UserDB();
    }

    private boolean AddNewAccount(){
        String username = UsernameField.getText().trim();
        String email = EmailField.getText().trim();
        String password1 = PasswordField.getText().trim();
        String password2 = RepeatPasswordField.getText().trim();

        if (username.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Пустые поля","Пожалуйста, заполните все поля.");
            return false;
        }

        if (!password1.equals(password2)){
            showAlert(Alert.AlertType.ERROR,"Пароли не совпадают","Пароли не совпадают, проверьте правильность пароля.");
            return false;
        }

        if (userDB.getUserByUsername(username)) {
            showAlert(Alert.AlertType.ERROR,"Пользователь существует", "Имя пользователя уже занято");
            return false;
        }

        try {
            User user = new User(0, username, password1, email);

            userDB.addNewUser(user);
            showAlert(Alert.AlertType.INFORMATION,"Регистрация пользователя", "Регистрация прошла успешно");
            return true;

        } catch (Exception e){
            throw new RuntimeException("Ошибка при создании пользователя: " + e.getMessage());
        }
    }

    private void openLoginWindow(){
        try {
            Stage currentStage = (Stage) RegisterButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/LoginView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            Stage stage = new Stage();
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
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
