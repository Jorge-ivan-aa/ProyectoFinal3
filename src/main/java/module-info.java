module co.edu.uniquindio.icaja {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.logging;
    requires java.desktop;
    requires org.mapstruct;


    opens co.edu.uniquindio.icaja to javafx.fxml;
    exports co.edu.uniquindio.icaja;
    exports co.edu.uniquindio.icaja.controller;
    opens co.edu.uniquindio.icaja.controller to javafx.fxml;
    exports co.edu.uniquindio.icaja.view;
    opens co.edu.uniquindio.icaja.view to javafx.fxml;
    exports co.edu.uniquindio.icaja.factory;
    opens co.edu.uniquindio.icaja.factory to javafx.fxml;
}