package com.todolist.Controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.todolist.DB.UserDB;
import com.todolist.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ResetPasswordController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField EmailField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private PasswordField RepeatPasswordField;

    @FXML
    private Button ResetPasswordButton;

    @FXML
    private TextField UsernameField;

    private UserDB userDB;

    @FXML
    void ResetPasswordHandle(ActionEvent event) {
        if (ResetPassword()){
            openLoginMenu();
        }
    }

    @FXML
    void initialize() {
        userDB = new UserDB();
    }

    private boolean ResetPassword(){
        String email = EmailField.getText();
        String username = UsernameField.getText();
        String newPassword = PasswordField.getText();
        String repeatPassword = RepeatPasswordField.getText();

        if (username.isEmpty() || email.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty()){
            showAlert(Alert.AlertType.ERROR,"Пустые поля","Пожалуйста, заполните все поля.");
            return false;
        }

        if (!newPassword.equals(repeatPassword)){
            showAlert(Alert.AlertType.ERROR,"Пароли не совпадают","Пароли не совпадают, проверьте правильность пароля.");
            return false;
        }

        if (newPassword.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Слабый пароль", "Пароль должен содержать минимум 6 символов.");
            return false;
        }

        try {
            if (!userDB.getUserByUsername(username)) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Такой пользователь не найден.");
                return false;
            }

            if (!userDB.emailExists(email)) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Email не зарегистрирован.");
                return false;
            }

            User user = userDB.getUserByUsernameAndEmail(username, email);
            if (user == null) {
                showAlert(Alert.AlertType.ERROR, "Ошибка", "Пользователь с такими данными не найден.");
                return false;
            }

            user.setPassword(newPassword);
            userDB.updatePassword(user);

            showAlert(Alert.AlertType.INFORMATION, "Готово", "Пароль успешно обновлён.");
        } catch (SQLException e) {
            System.out.println("Ошибка при восстановлении пароля: " + e.getMessage());
        }
        return true;
    }

    private void openLoginMenu() {
        try {
            Stage currentStage = (Stage) ResetPasswordButton.getScene().getWindow();

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
