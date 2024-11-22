package co.edu.uniquindio.icaja.services;

public interface RabbitMessageListener {
    void onMessageReceived(Object message);
}
