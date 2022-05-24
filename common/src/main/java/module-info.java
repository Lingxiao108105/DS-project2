module common {
    requires javafx.controls;
    requires javafx.fxml;
    requires sofa.rpc.all;
    requires lombok;

    // export Application subclass's package
    exports edu.service;

}