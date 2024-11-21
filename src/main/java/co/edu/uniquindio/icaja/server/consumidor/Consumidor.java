package co.edu.uniquindio.icaja.server.consumidor;

import co.edu.uniquindio.icaja.server.consumidor.controller.ModelFactoryController;

public class Consumidor {
    public static ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

    public static void escuchandoActualizaciones() {
        modelFactoryController.consumirMensajesServicio();
    }
}
