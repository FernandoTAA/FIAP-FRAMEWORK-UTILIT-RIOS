package br.com.fiap.roupas.consumer;

import br.com.fiap.roupas.service.InvoicePdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class InvoicePdfGeneratorConsumer {

    @Autowired
    private InvoicePdfService invoicePdfService;

    @JmsListener(destination = "invoice-pdf-generator")
    public void receiveQueue(Long invoiceCounter) {
        invoicePdfService.generatePdfFile(invoiceCounter);
    }

}
