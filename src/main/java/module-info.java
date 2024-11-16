module co.edu.uniquindio.icaja {
    requires static lombok;
    requires MaterialFX;
    requires atlantafx.base;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.logging;
    requires jbcrypt;

    exports co.edu.uniquindio.icaja.mapping.mappers;
    exports co.edu.uniquindio.icaja.mapping.dto;
    exports co.edu.uniquindio.icaja.mapping.services;
    opens co.edu.uniquindio.icaja to javafx.fxml;
    exports co.edu.uniquindio.icaja.model;
    exports co.edu.uniquindio.icaja.model.enums;
    exports co.edu.uniquindio.icaja.model.services;
    exports co.edu.uniquindio.icaja.model.persistencia;
    exports co.edu.uniquindio.icaja;
    exports co.edu.uniquindio.icaja.controller;
    exports co.edu.uniquindio.icaja.controller.enums;
    opens co.edu.uniquindio.icaja.controller to javafx.fxml;
    exports co.edu.uniquindio.icaja.view;
    opens co.edu.uniquindio.icaja.view to javafx.fxml;
    exports co.edu.uniquindio.icaja.factory;
    opens co.edu.uniquindio.icaja.factory to javafx.fxml;
    exports co.edu.uniquindio.icaja.view.views.admin;
    opens co.edu.uniquindio.icaja.view.views.admin to javafx.fxml;
    exports co.edu.uniquindio.icaja.view.views.normal;
    opens co.edu.uniquindio.icaja.view.views.normal to javafx.fxml;
    exports co.edu.uniquindio.icaja.view.views;
    opens co.edu.uniquindio.icaja.view.views to javafx.fxml;
    exports co.edu.uniquindio.icaja.utils.loggin;
    opens co.edu.uniquindio.icaja.utils.loggin to javafx.fxml;
    exports co.edu.uniquindio.icaja.utils.respaldo;
    opens co.edu.uniquindio.icaja.utils.respaldo to javafx.fxml;
    exports co.edu.uniquindio.icaja.utils.tools;
    opens co.edu.uniquindio.icaja.utils.tools to javafx.fxml;

    // excepciones
    exports co.edu.uniquindio.icaja.exception.constructores;
    exports co.edu.uniquindio.icaja.exception.transacciones;
    exports co.edu.uniquindio.icaja.exception.almacenamiento;
    exports co.edu.uniquindio.icaja.exception.login;
    exports co.edu.uniquindio.icaja.exception.crud;
}