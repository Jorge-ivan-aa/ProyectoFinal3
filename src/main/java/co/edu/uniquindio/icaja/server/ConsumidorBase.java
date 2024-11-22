package co.edu.uniquindio.icaja.server;

import co.edu.uniquindio.icaja.server.config.RabbitFactory;
import co.edu.uniquindio.icaja.server.mapping.MensajeDTO;
import co.edu.uniquindio.icaja.server.services.Consumidor;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConsumidorBase {
    private static ConsumidorBase instancia;

    private final Channel canal;

    private ConsumidorBase() throws Exception {
        ConnectionFactory factory = new RabbitFactory().getConnectionFactory();
        Connection conexion = factory.newConnection();
        canal = conexion.createChannel();
        canal.queueDeclare("cola_mensajes", false, false, false, null); // Declaramos la cola
    }

    public static ConsumidorBase obtenerInstancia() throws Exception {
        if (instancia == null) {
            instancia = new ConsumidorBase();
        }
        return instancia;
    }


    /**
     * Inicia el consumo de mensajes desde la cola "cola_mensajes".
     * Cuando un mensaje es recibido, se crea un DTO a partir del mensaje
     * y se pasa al consumidor para que lo procese.
     *
     * @param consumidor El objeto Consumidor que procesará el DTO.
     * @throws Exception Si ocurre algún error al consumir el mensaje.
     */
    public void consumirMensaje(Consumidor consumidor) throws Exception {
        // Llamamos al consumidor para recibir el mensaje
        canal.basicConsume("cola_sync", true, new DefaultConsumer(canal) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String mensaje = new String(body, StandardCharsets.UTF_8);
                MensajeDTO dto = new MensajeDTO(mensaje);  // Creamos el DTO a partir del mensaje recibido
                procesarMensaje(dto, consumidor);
            }
        });
    }

    /**
     * Procesa el mensaje recibido pasándolo al consumidor.
     * Este método se encarga de delegar el procesamiento del DTO al consumidor.
     *
     * @param dto El DTO que contiene los datos del mensaje recibido.
     * @param consumidor El objeto Consumidor que procesará el DTO.
     */
    private void procesarMensaje(MensajeDTO dto, Consumidor consumidor) {
        consumidor.procesarDTO(dto);
    }

}