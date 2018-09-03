package br.com.fiap.roupas.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Invoice {

    @Id
    private String id;

    private Long invoiceCounter;
    private Long operationOrderCounter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;
    private String authenticationHash;

    private Company company;
    private List<Item> items;
}
