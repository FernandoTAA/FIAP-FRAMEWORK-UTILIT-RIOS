package br.com.fiap.roupas.repository;

import br.com.fiap.roupas.entity.Invoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InvoiceRepository extends MongoRepository<Invoice, String> {

    Invoice findFirstByInvoiceCounter(Long invoiceCounter);

    List<Invoice> findByDateTimeBetween(LocalDateTime from, LocalDateTime to);

}
