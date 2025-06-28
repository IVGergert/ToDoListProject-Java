module com.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.todolist to javafx.fxml;
    opens com.todolist.Controllers to javafx.fxml;
    exports com.todolist;
}