package br.com.fiap.roupas.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemVO {
	
	private Integer order;
	private BigDecimal quantity;
	private ProductVO product;
	
	public BigDecimal getTotalValue() {
		return quantity.multiply(product.getUnitValue());
	}

}
