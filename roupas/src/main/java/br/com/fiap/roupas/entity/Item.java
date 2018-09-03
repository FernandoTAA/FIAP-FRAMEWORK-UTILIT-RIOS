package br.com.fiap.roupas.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Item {

    private Integer order;
    private BigDecimal quantity;
    private Product product;

    public BigDecimal getTotalValue() {
        return quantity.multiply(product.getUnitValue());
    }

}
