module co.edu.uniquindio.icaja {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.logging;
    requires java.desktop;


    exports co.edu.uniquindio.icaja.mapping.mappers;
    exports co.edu.uniquindio.icaja.mapping.dto;
    opens co.edu.uniquindio.icaja to javafx.fxml;
    exports co.edu.uniquindio.icaja.model;
    exports co.edu.uniquindio.icaja;
    exports co.edu.uniquindio.icaja.controller;
    opens co.edu.uniquindio.icaja.controller to javafx.fxml;
    exports co.edu.uniquindio.icaja.view;
    opens co.edu.uniquindio.icaja.view to javafx.fxml;
    exports co.edu.uniquindio.icaja.factory;
    opens co.edu.uniquindio.icaja.factory to javafx.fxml;
    exports co.edu.uniquindio.icaja.model.factories;
    exports co.edu.uniquindio.icaja.utils;
    opens co.edu.uniquindio.icaja.utils to javafx.fxml;
    exports co.edu.uniquindio.icaja.view.views.admin;
    opens co.edu.uniquindio.icaja.view.views.admin to javafx.fxml;
    exports co.edu.uniquindio.icaja.view.views;
    opens co.edu.uniquindio.icaja.view.views to javafx.fxml;
}