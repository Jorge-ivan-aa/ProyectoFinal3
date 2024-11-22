package co.edu.uniquindio.icaja.server;

import co.edu.uniquindio.icaja.server.config.Cola;
import co.edu.uniquindio.icaja.server.config.RabbitFactory;
import co.edu.uniquindio.icaja.server.mapping.MensajeDTO;
import co.edu.uniquindio.icaja.server.mapping.MensajeMapper;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ProductorBase {
    private static ProductorBase instancia;
    private final Channel canal;

    private ProductorBase() throws Exception {
        ConnectionFactory factory = new RabbitFactory().getConnectionFactory();
        Connection conexion = factory.newConnection();
        this.canal = conexion.createChannel();
        canal.queueDeclare(Cola.COLA_SYNC.getCola(), false, false, false, null);
    }

    public static ProductorBase obtenerInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ProductorBase();
        }
        return instancia;
    }

    /**
     * Envía un mensaje a la cola "miCola".
     * El mensaje se convierte desde un DTO (Objeto de Transferencia de Datos) a un formato JSON (String).
     *
     * @param dto El DTO que contiene los datos que se enviarán en el mensaje.
     * @throws IOException Si ocurre algún error durante el envío del mensaje.
     */
    public void enviarMensaje(MensajeDTO dto) throws IOException {
        String mensaje = MensajeMapper.toJson(dto);  // Convierte DTO a JSON o string
        canal.basicPublish("", Cola.COLA_SYNC.getCola(), null, mensaje.getBytes());
    }
}
