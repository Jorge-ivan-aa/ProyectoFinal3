package co.edu.uniquindio.icaja.server.consumidor.controller;



import co.edu.uniquindio.icaja.server.config.RabbitFactory;
import co.edu.uniquindio.icaja.server.consumidor.controller.service.IModelFactoryService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import static co.edu.uniquindio.icaja.server.util.Constantes.QUEUE_NUEVA_PUBLICACION;

public class ModelFactoryController implements IModelFactoryService, Runnable {

    RabbitFactory rabbitFactory;
    ConnectionFactory connectionFactory;

    Thread hiloServicioConsumer1;



    //------------------------------  Singleton ------------------------------------------------
    // Clase estatica oculta. Tan solo se instanciara el singleton una vez
    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public ModelFactoryController() {
        initRabbitConnection();
    }

    private void initRabbitConnection() {
        rabbitFactory = new RabbitFactory();
        connectionFactory = rabbitFactory.getConnectionFactory();
        System.out.println("conexion establecidad");
    }

    public void consumirMensajesServicio(){
        hiloServicioConsumer1 = new Thread(this);
        hiloServicioConsumer1.start();
    }


    @Override
    public void run() {
        Thread currentThread = Thread.currentThread();
        if(currentThread == hiloServicioConsumer1){
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
                System.out.println("Mensaje recibido: " + message);



            };

            while (true) {
                channel.basicConsume(QUEUE_NUEVA_PUBLICACION, true, deliverCallback, consumerTag -> { });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}