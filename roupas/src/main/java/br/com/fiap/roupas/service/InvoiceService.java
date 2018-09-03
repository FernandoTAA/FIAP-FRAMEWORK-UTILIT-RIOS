package br.com.fiap.roupas.service;

import br.com.fiap.roupas.entity.Invoice;
import br.com.fiap.roupas.producer.InvoicePdfGeneratorProducer;
import br.com.fiap.roupas.repository.InvoiceRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
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
        log.info(String.format("BEGIN: saveInvoice(%d)", invoice.getInvoiceCounter()));
        Invoice savedInvoice = invoiceRepository.save(invoice);
        log.info(String.format("END: saveInvoice(%d)", invoice.getInvoiceCounter()));
        return savedInvoice;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Invoice getInvoice(Long invoiceCounter) {
        log.info(String.format("BEGIN: getInvoice(%d)", invoiceCounter));
        Invoice invoice = invoiceRepository.findFirstByInvoiceCounter(invoiceCounter);
        log.info(String.format("END: getInvoice(%d)", invoiceCounter));
        return invoice;
    }

    @Transactional
    public Boolean generatePdfById(Long invoiceCounter) {
        try {
            log.info(String.format("BEGIN: generatePdfById(%d)", invoiceCounter));
            invoicePdfGeneratorProducer.publish(invoiceCounter);
            log.info(String.format("END: generatePdfById(%d)", invoiceCounter));
            return true;
        } catch (Exception e) {
            log.error(String.format("ERROR: generatePdfById(%d)", invoiceCounter), e);
            return false;
        }
    }

    @Transactional
    public List<Long> generatePdfByRangeOfDate(LocalDateTime initDateTime, LocalDateTime finalDateTime) {
        log.info(String.format("BEGIN: generatePdfByRangeOfDate(%s, %s)", initDateTime, initDateTime));
        List<Long> invoiceCounters = invoiceRepository.findByDateTimeBetween(initDateTime, finalDateTime).stream().
                map(Invoice::getInvoiceCounter).collect(Collectors.toList());
        invoicePdfGeneratorProducer.publish(invoiceCounters.stream().toArray(Long[]::new));
        log.info(String.format("END: generatePdfByRangeOfDate(%s, %s)", initDateTime, initDateTime));
        return invoiceCounters;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Optional<byte[]> getPdf(Long invoiceCounter) {
        log.info(String.format("BEGIN: getPdf(%d)", invoiceCounter));
        byte[] binary = invoicePdfService.getPdfBinary(invoiceCounter);

        Optional<byte[]> optionalBinary = Optional.empty();
        if (binary != null) {
            log.info(String.format("EMPTY: getPdf(%d)", invoiceCounter));
            optionalBinary = Optional.of(binary);
        }

        log.info(String.format("END: getPdf(%d)", invoiceCounter));
        return optionalBinary;
    }
}
