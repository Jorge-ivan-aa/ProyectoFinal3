package co.edu.uniquindio.icaja.server.services;

import co.edu.uniquindio.icaja.server.mapping.MensajeDTO;

public interface Consumidor {

    /**
     * Inicia el proceso de consumo de mensajes desde la cola de RabbitMQ.
     * Este método se encarga de escuchar la cola de mensajes y, cuando se recibe un mensaje,
     * lo convierte en un DTO (Data Transfer Object) y lo pasa al consumidor para su procesamiento.
     *
     * @throws Exception Si ocurre algún error al intentar consumir mensajes de la cola
     */
    void consumirMensaje() throws Exception;

    /**
     * Procesa el DTO (Data Transfer Object) recibido.
     * Este método es implementado por los consumidores para definir la lógica
     * específica de cómo manejar el DTO recibido (por ejemplo, mostrar en la interfaz de usuario).
     *
     * @param dto El DTO que contiene los datos del mensaje recibido de la cola.
     *            El DTO debe contener la información que será procesada por el consumidor.
     */
    void procesarDTO(MensajeDTO dto);
}
