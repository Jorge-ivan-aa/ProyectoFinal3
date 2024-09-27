module co.edu.uniquindio.proyectofinal3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens co.edu.uniquindio.proyectofinal3 to javafx.fxml;
    exports co.edu.uniquindio.proyectofinal3;
    exports co.edu.uniquindio.proyectofinal3.controller;
    opens co.edu.uniquindio.proyectofinal3.controller to javafx.fxml;
}