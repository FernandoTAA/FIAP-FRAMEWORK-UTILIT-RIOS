package br.com.fiap.roupas.entity;

import lombok.Data;

@Data
public class Company {

    private String name;
    private Long companyNationalRegistration;
    private String stateRegistration;
    private String cityRegistration;
    private Address Address;

}
