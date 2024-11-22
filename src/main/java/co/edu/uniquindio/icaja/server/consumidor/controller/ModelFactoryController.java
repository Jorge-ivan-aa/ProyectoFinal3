package co.edu.uniquindio.icaja.server.consumidor.controller;

import co.edu.uniquindio.icaja.server.config.RabbitFactory;
import co.edu.uniquindio.icaja.factory.ModelFactory;
import co.edu.uniquindio.icaja.server.consumidor.controller.service.IModelFactoryService;
import co.edu.uniquindio.icaja.utils.loggin.Seguimiento;
import com.rabbitmq.client.*;

import static co.edu.uniquindio.icaja.server.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class ModelFactoryController implements IModelFactoryService, Runnable {
    private ConnectionFactory connectionFactory;
    private Thread hiloServicioConsumer1;
    private final ModelFactory factory = ModelFactory.getInstance(); // Instancia de ModelFactory

    // Singleton revisado
    private static ModelFactoryController instance;

    private ModelFactoryController() {
        initRabbitConnection();
    }

    public static synchronized ModelFactoryController getInstance() {
        if (instance == null) {
            instance = new ModelFactoryController();
        }
        return instance;
    }

    private void initRabbitConnection() {
        RabbitFactory rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("Conexión establecida");
    }

    public void consumirMensajesServicio() {
        hiloServicioConsumer1 = new Thread(this);
        hiloServicioConsumer1.start();
    }

    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        if (currentThread == hiloServicioConsumer1) {
            consumirMensajes();
        }
    }

    private void consumirMensajes() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NUEVA_PUBLICACION, false, false, false, null);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody());
                Seguimiento.registrarLog(2, message);
                factory.setIcaja();
                factory.sincronizarData();
                factory.getNotificaciones().add(message);
            };

            while (true) {
                channel.basicConsume(QUEUE_NUEVA_PUBLICACION, true, deliverCallback, consumerTag -> { });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
