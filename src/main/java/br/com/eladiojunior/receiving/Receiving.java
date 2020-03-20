package br.com.eladiojunior.receiving;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Receiving {
    private final static String QUEUE_NAME = "notificacao";
    private final static String HOST_NAME = "192.168.0.51";
    private final static int HOST_PORT = 5672;
    private static Logger logger = null;

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(Receiving.class);
        Receiving receiving = new Receiving();
        receiving.startFila();
    }

    private void startFila() {

        try {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST_NAME);
            factory.setPort(HOST_PORT);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" Aguardando as mensagens. Para finalizar prescione CTRL+C.");

            DeliverCallback deliverCallback = new DeliverCallback() {
                public void handle(String consumerTag, Delivery delivery) throws IOException {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println(" [x] Recebida: [" + message + "]");
                }
            };

            channel.basicConsume(QUEUE_NAME, true, deliverCallback, new CancelCallback() {
                public void handle(String consumerTag) {
                    System.out.println(" [t] Tag: [" + consumerTag + "]");
                }
            });

        } catch (Exception erro) {
            logger.error("Start da Fila", erro);
        }
    }
}
