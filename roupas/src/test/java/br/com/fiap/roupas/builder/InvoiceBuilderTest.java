package br.com.fiap.roupas.builder;

import br.com.fiap.roupas.entity.Address;
import br.com.fiap.roupas.entity.Company;
import br.com.fiap.roupas.entity.Item;
import br.com.fiap.roupas.entity.Product;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class InvoiceBuilderTest {

    @Test
    public void buildTest() {
        InvoiceBuilder invoiceBuilder = new InvoiceBuilder();
        invoiceBuilder.setInvoiceCounter(1L);
        invoiceBuilder.setOperationOrderCounter(2L);
        invoiceBuilder.setAuthenticationHash("37ECGC22 A173F3M3 APS4EGGD 23EBA655 A16499B4 58F4");
        invoiceBuilder.setDateTime(LocalDateTime.of(2018, Month.AUGUST, 18, 12, 14, 5));

        Company company = new Company();
        company.setName("FIAP Roupas");
        company.setCompanyNationalRegistration(11_111_111_1111_11L);
        company.setStateRegistration("123.456.789.XXX");
        company.setCityRegistration("12345-X");
        company.setAddress(new Address());
        company.getAddress().setAddress("Av. Paulista");
        company.getAddress().setNumber("1200");
        company.getAddress().setComplement("5º andar");
        company.getAddress().setCity("São Paulo");
        company.getAddress().setState("SP");
        invoiceBuilder.setCompany(company);

        List<Item> items = new ArrayList<>();
        items.add(newItem(10L, "Camiseta P", "UN", new BigDecimal("25.75"), 1, new BigDecimal(2)));
        items.add(newItem(200L, "Camiseta M", "PC", new BigDecimal("30.25"), 2, new BigDecimal(1)));
        items.add(newItem(3000L, "Camiseta G", "PC", new BigDecimal("54.10"), 3, new BigDecimal(10)));
        items.add(newItem(40000L, "Calça P", "UN", new BigDecimal("89.99"), 4, new BigDecimal(200)));

        invoiceBuilder.setItems(items);
        Assert.assertEquals(getExpectedInvoiceText(), invoiceBuilder.build());
    }

    private String getExpectedInvoiceText() {
        StringBuilder expected = new StringBuilder();
        expected.append("                         FIAP Roupas                         \n");
        expected.append("               Av. Paulista - 1200 - 5º andar                \n");
        expected.append("                       São Paulo - SP                        \n");
        expected.append("\n");
        expected.append("CNPJ: 11111111111111\n");
        expected.append("IE: 123.456.789.XXX\n");
        expected.append("IM: 12345-X\n");
        expected.append("-------------------------------------------------------------\n");
        expected.append("18/08/2018 12:14:05     CCF: 000001              COO: 000002\n");
        expected.append("-------------------------------------------------------------\n");
        expected.append("ITEM CÓDIGO DESCRIÇÃO          QTD   UN   VL.UNIT.  VL.TOTAL.\n");
        expected.append("-------------------------------------------------------------\n");
        expected.append("0001 000010 Camiseta P       2,000   UN      25,75      51,50\n");
        expected.append("0002 000200 Camiseta M       1,000   PC      30,25      30,25\n");
        expected.append("0003 003000 Camiseta G      10,000   PC      54,10     541,00\n");
        expected.append("0004 040000 Calça P        200,000   UN      89,99   17998,00\n");
        expected.append("-------------------------------------------------------------\n");
        expected.append("37ECGC22 A173F3M3 APS4EGGD 23EBA655 A16499B4 58F4");
        return expected.toString();
    }

    private Item newItem(Long id, String name, String unitMetric, BigDecimal unitValue, Integer order,
                         BigDecimal quantity) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setUnitMetric(unitMetric);
        product.setUnitValue(unitValue);

        Item item1 = new Item();
        item1.setOrder(order);
        item1.setQuantity(quantity);
        item1.setProduct(product);
        return item1;
    }

}
