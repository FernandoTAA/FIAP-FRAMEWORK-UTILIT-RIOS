package br.com.fiap.roupas.service;

import br.com.fiap.roupas.builder.InvoiceBuilder;
import br.com.fiap.roupas.builder.TextPDFBuilder;
import br.com.fiap.roupas.entity.Invoice;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Service
public class InvoicePdfService {

    @Autowired
    private InvoiceService invoiceService;

    @Transactional
    public void generatePdfFile(Long invoiceCounter) {
        log.info(String.format("BEGIN: generatePdfFile(%d)", invoiceCounter));
        Invoice invoice = invoiceService.getInvoice(invoiceCounter);

        InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
        invoiceBuilder.setInvoiceCounter(invoice.getInvoiceCounter());
        invoiceBuilder.setOperationOrderCounter(invoice.getOperationOrderCounter());
        invoiceBuilder.setAuthenticationHash(invoice.getAuthenticationHash());
        invoiceBuilder.setDateTime(invoice.getDateTime());
        invoiceBuilder.setCompany(invoice.getCompany());
        invoiceBuilder.setItems(invoice.getItems());

        String invoiceText = invoiceBuilder.build();

        File file = getPdfFile(invoiceCounter);

        TextPDFBuilder textPDFBuilder = new TextPDFBuilder();
        textPDFBuilder.setFile(file);
        textPDFBuilder.setText(invoiceText);

        textPDFBuilder.build();
        log.info(String.format("END: generatePdfFile(%d)", invoiceCounter));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public byte[] getPdfBinary(Long invoiceCounter) {
        log.info(String.format("BEGIN: getPdfBinary(%d)", invoiceCounter));
        File file = getPdfFile(invoiceCounter);

        if (!file.exists() && !file.isFile()) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] binary = IOUtils.toByteArray(inputStream);
            log.info(String.format("END: getPdfBinary(%d)", invoiceCounter));
            return binary;
        } catch (IOException e) {
            log.error(String.format("ERROR: getPdfBinary(%d)", invoiceCounter), e);
            throw new RuntimeException(e);
        }
    }

    private File getPdfFile(Long invoiceCounter) {
        String fileName = String.format("invoice-%d", invoiceCounter);
        return new File(String.format("./invoices-files/%s.pdf", fileName));
    }
}
