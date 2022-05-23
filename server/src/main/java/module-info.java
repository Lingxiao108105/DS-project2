module server {
    requires javafx.controls;
    requires javafx.fxml;

    // open any FXML controller class' packages to at least javafx.fxml
    //opens edu.javafx to javafx.fxml;

    // export Application subclass's package
    exports edu;

}