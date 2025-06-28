package com.todolist.Controllers;

import java.net.URL;
import java.util.List;
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
import javafx.stage.Window;

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

    public void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean AddNewAccount(){
        String username = UsernameField.getText().trim();
        String email = EmailField.getText().trim();
        String password1 = PasswordField.getText().trim();
        String password2 = RepeatPasswordField.getText().trim();

        if (username.isEmpty() || email.isEmpty() || password1.isEmpty() || password2.isEmpty()){
            showErrorAlert("Пустые поля","Введите имя пользователя, пароль или почту.");
            return false;
        }

        if (!password1.equals(password2)){
            showErrorAlert("Пароли не совпадают","Пароли не совпадают, проверьте правильность пароля.");
            return false;
        }

        if (userDB.getUserByUsername(username)) {
            showErrorAlert("Пользователь существует", "Имя пользователя уже занято");
            return false;
        }

        try {
            User user = new User(0, username, password1, email);

            userDB.addNewUser(user);
            showErrorAlert("Регистрация пользователя", "Регистрация прошла успешно");
            return true;

        } catch (Exception e){
            showErrorAlert("Ошибка базы данных", "Не удалось создать пользователя: " + e.getMessage());
            return false;
        }
    }

    private void openLoginWindow(){
        try {
            Stage currentStage = (Stage) RegisterButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/todolist/FilesFxml/LoginView.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

//             TodoListController controller = loader.getController();
//             controller.setUser(user);

            Stage stage = new Stage();
            stage.setTitle("Авторизация");
            stage.setScene(scene);
            currentStage.close();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Ошибка открытия ToDoList","Ошибка при открытии списка задач.");
        }
    }

}
