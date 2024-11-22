package co.edu.uniquindio.icaja.server.services;

import co.edu.uniquindio.icaja.server.mapping.MensajeDTO;

public interface Productor {

    /**
     * Envía un mensaje a la cola de RabbitMQ.
     * Este método se encarga de convertir un DTO (Data Transfer Object) en un mensaje que será
     * enviado a la cola correspondiente en RabbitMQ para que los consumidores lo reciban y procesen.
     *
     * @param dto El DTO que contiene los datos que se enviarán como mensaje en la cola de RabbitMQ.
     *            Este DTO debe ser convertido a un formato adecuado (por ejemplo, una cadena)
     *            para ser transmitido por la cola.
     *
     * @throws Exception Si ocurre algún error al intentar enviar el mensaje a la cola.
     */
    void enviarMensaje(MensajeDTO dto) throws Exception;

}
