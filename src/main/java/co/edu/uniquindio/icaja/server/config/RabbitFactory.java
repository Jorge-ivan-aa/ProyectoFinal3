package co.edu.uniquindio.icaja.server.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.Getter;

import java.util.UUID;

@Getter
public class RabbitFactory {
    private final ConnectionFactory connectionFactory;
    private String instanceId = "";


    public RabbitFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localhost");
        this.connectionFactory.setPort(5672);
        this.connectionFactory.setUsername("guest");
        this.connectionFactory.setPassword("guest");
        instanceId = UUID.randomUUID().toString();
    }

}