package co.edu.uniquindio.icaja.server.productor;

import co.edu.uniquindio.icaja.server.productor.controller.ModelFactoryController;

import static co.edu.uniquindio.icaja.server.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class Productor {

    private static ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

    public static void enviarNotificacion(String mjs) {
        modelFactoryController.producirMensaje(QUEUE_NUEVA_PUBLICACION, mjs);
    }
}
