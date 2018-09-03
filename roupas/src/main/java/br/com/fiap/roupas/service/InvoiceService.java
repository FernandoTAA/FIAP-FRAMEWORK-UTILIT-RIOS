package br.com.fiap.roupas.service;

import br.com.fiap.roupas.entity.Invoice;
import br.com.fiap.roupas.producer.InvoicePdfGeneratorProducer;
import br.com.fiap.roupas.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoicePdfGeneratorProducer invoicePdfGeneratorProducer;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoicePdfService invoicePdfService;

    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Invoice getInvoice(Long invoiceCounter) {
        return invoiceRepository.findFirstByInvoiceCounter(invoiceCounter);
    }

    @Transactional
    public Boolean generatePdfById(Long invoiceCounter) {
        try {
            invoicePdfGeneratorProducer.publish(invoiceCounter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public List<Long> generatePdfByRangeOfDate(LocalDateTime initDateTime, LocalDateTime finalDateTime) {
        List<Long> invoiceCounters = invoiceRepository.findByDateTimeBetween(initDateTime, finalDateTime).stream().
                map(Invoice::getInvoiceCounter).collect(Collectors.toList());
        invoicePdfGeneratorProducer.publish(invoiceCounters.stream().toArray(Long[]::new));
        return invoiceCounters;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<byte[]> getPdf(Long invoiceCounter) {
        byte[] binary = invoicePdfService.getPdfBinary(invoiceCounter);

        if (binary != null) {
            return Optional.of(binary);
        }
        return Optional.empty();
    }
}
