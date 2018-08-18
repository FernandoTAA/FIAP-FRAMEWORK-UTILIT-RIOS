package br.com.fiap.roupas.vo;

import lombok.Data;

@Data
public class CompanyVO {

	private String name;
	private Long companyNationalRegistration;
	private String stateRegistration;
	private String cityRegistration;
	private AddressVO Address;
	
}
