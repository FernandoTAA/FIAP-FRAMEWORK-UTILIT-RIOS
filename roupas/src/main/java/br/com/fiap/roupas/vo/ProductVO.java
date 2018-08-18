package br.com.fiap.roupas.vo;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductVO {

	private Long id;
	private String name;
	private String unitMetric;
	private BigDecimal unitValue;
	
}
