package br.com.eladiojunior.sending;

import br.com.eladiojunior.model.Email;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Sending {
    private final static String QUEUE_NAME = "notificacao";
    private final static String HOST_NAME = "192.168.0.51";
    private final static int HOST_PORT = 5672;
    private static Logger logger = null;

    private Channel channel = null;
    private boolean isSending;

    public boolean isSending() {
        return isSending;
    }

    public static void main(String[] args) {
        logger = LoggerFactory.getLogger(Sending.class);
        Sending sending = new Sending();
        if (!sending.isSending()) {
            System.out.println("Não existe conexão ativa para enviar as mensagens.");
        }
        Email email;
        for (int i=1; i <= 100000; i++) {
            email = new Email("remetente@dominio.com.br", String.format("destinatario_%s@dominio.cassi.com.br", i), String.format("Assunto [%s]", i), String.format("Mensagem [%s] do corpo do e-mail.", i));
            sending.enviarEmail(email);
        }
    }

    public Sending() {
        try {
            isSending = false;
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST_NAME);
            factory.setPort(HOST_PORT);
            Connection connection = factory.newConnection();
            this.channel = connection.createChannel();
            this.channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            isSending = true;
        } catch (Exception erro) {
            logger.error("Criando Serviço de Envio", erro);
        }
    }

    private void enviarEmail(Email email) {
        try {
            String mensagem = email.toJson();
            this.channel.basicPublish("", QUEUE_NAME, null, mensagem.getBytes());
        } catch (Exception erro) {
            logger.error("Envio da mensagem", erro);
        }
    }
}
