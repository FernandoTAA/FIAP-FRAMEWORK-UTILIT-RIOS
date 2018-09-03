package br.com.fiap.roupas.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.Arrays;


@Component
public class InvoicePdfGeneratorProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void publish(Long... invoiceCounter) {
        Arrays.stream(invoiceCounter).forEach(this::send);
    }

    private void send(Long invoiceCounter) {
        this.jmsMessagingTemplate.convertAndSend(this.queue, invoiceCounter);
    }
}
