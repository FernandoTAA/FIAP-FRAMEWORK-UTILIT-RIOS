package br.com.fiap.roupas.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {

    private Long id;
    private String name;
    private String unitMetric;
    private BigDecimal unitValue;

}
