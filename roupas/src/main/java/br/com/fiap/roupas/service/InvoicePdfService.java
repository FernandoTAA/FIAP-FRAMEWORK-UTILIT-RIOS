package br.com.fiap.roupas.service;

import br.com.fiap.roupas.builder.InvoiceBuilder;
import br.com.fiap.roupas.builder.TextPDFBuilder;
import br.com.fiap.roupas.entity.Invoice;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.ReaderInputStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.time.LocalDateTime;

@Service
public class InvoicePdfService {

    @Autowired
    private InvoiceService invoiceService;

    @Transactional
    public void generatePdfFile(Long invoiceCounter) {
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
    }

    public byte[] getPdfBinary(Long invoiceCounter) {
        File file = getPdfFile(invoiceCounter);

        if (!file.exists() && !file.isFile()) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getPdfFile(Long invoiceCounter) {
        String fileName = String.format("invoice-%d.pdf", invoiceCounter);
        return new File(String.format("./invoices-files/%s.pdf", fileName));
    }
}
