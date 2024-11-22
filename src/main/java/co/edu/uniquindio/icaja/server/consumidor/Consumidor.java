package co.edu.uniquindio.icaja.server.consumidor;

import co.edu.uniquindio.icaja.server.consumidor.controller.ModelFactoryController;

public class Consumidor {

    private static ModelFactoryController modelFactoryController;

    public Consumidor() {
        // Inicializamos explícitamente el controlador
        modelFactoryController = ModelFactoryController.getInstance();
        if (modelFactoryController != null) {
            modelFactoryController.consumirMensajesServicio();
        } else {
            // Agregar un manejo de error si la instancia no se pudo inicializar
            System.out.println("Error al inicializar ModelFactoryController");
        }
    }

    public static void escuchandoActualizaciones() {
        if (modelFactoryController != null) {
            modelFactoryController.consumirMensajesServicio();
        }
    }
}

