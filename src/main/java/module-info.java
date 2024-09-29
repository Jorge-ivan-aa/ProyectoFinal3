module co.edu.uniquindio.icaja {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.icaja to javafx.fxml;
    exports co.edu.uniquindio.icaja;
    exports co.edu.uniquindio.icaja.controller;
    opens co.edu.uniquindio.icaja.controller to javafx.fxml;
    exports co.edu.uniquindio.icaja.factory;
    opens co.edu.uniquindio.icaja.factory to javafx.fxml;
}