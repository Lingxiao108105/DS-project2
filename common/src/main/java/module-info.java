module common {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires sofa.rpc.all;
    requires lombok;
    requires java.desktop;

    // export Application subclass's package
    exports edu.service;
    exports edu.dto;
    exports edu.dto.Impl;
    exports edu.common.util;
    exports edu.common.exception;
    exports edu.common.enums;

}