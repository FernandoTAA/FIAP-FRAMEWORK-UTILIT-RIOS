package br.com.fiap.roupas.rest;

import br.com.fiap.roupas.entity.Invoice;
import br.com.fiap.roupas.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceRest {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public Invoice post(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @GetMapping("/{invoiceCounter}")
    public Invoice getById(@PathVariable("invoiceCounter") Long invoiceCounter) {
        return invoiceService.getInvoice(invoiceCounter);
    }

    @PostMapping("/pdf/{invoiceCounter}")
    public Boolean generatePdfById(@PathVariable("invoiceCounter") Long invoiceCounter) {
        return invoiceService.generatePdfById(invoiceCounter);
    }

    @PostMapping("/pdf/init-date-time/{init-date-time}/final-date-time/{final-date-time}")
    public List<Long> generatePdfByRangeOfDate(
            @PathVariable("init-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime initDateTime,
            @PathVariable("final-date-time") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime finalDateTime) {
        return invoiceService.generatePdfByRangeOfDate(initDateTime, finalDateTime);
    }

    @GetMapping(value = "/pdf/{invoiceCounter}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> getPdfById(@PathVariable("invoiceCounter") Long invoiceCounter) {
        Optional<byte[]> binary = invoiceService.getPdf(invoiceCounter);
        String filename = String.format("invoice-%d.pdf", invoiceCounter);
        HttpHeaders headers = generatePdfHeader(filename);

        ResponseEntity<byte[]> responseEntity;
        if (binary.isPresent()) {
            responseEntity = new ResponseEntity<>(binary.get(), headers, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    private HttpHeaders generatePdfHeader(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }
}
