package br.com.fiap.roupas.builder;

import br.com.fiap.roupas.entity.Company;
import br.com.fiap.roupas.entity.Item;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Setter
public class InvoiceBuilder {

    private static final Integer TOTAL_COLUMNS = 61;
    private static final String SEPARATOR = StringUtils.repeat("-", TOTAL_COLUMNS);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
    private static final DecimalFormat FIVE_DIGITS_DECIMAL_FORMAT = new DecimalFormat("000000");
    private static final DecimalFormat THREE_DIGITS_DECIMAL_FORMAT = new DecimalFormat("0000");
    private static final DecimalFormat TWO_FRACTION_DIGITS_DECIMAL_FORMAT = new DecimalFormat("#0.00");
    private static final DecimalFormat THREE_FRACTION_DIGITS_DECIMAL_FORMAT = new DecimalFormat("#0.000");

    private Long invoiceCounter;
    private Long operationOrderCounter;
    private LocalDateTime dateTime;
    private String authenticationHash;

    private Company company;
    private List<Item> items;

    public String build() {
        Integer[] sizeColumns = {5, 7, 12, 10, 5, 11, 11};
        Integer columnIndex;
        Integer oneThirdFromTotalColumns = TOTAL_COLUMNS / 3;

        StringBuilder invoiceText = new StringBuilder();
        invoiceText.append(StringUtils.center(company.getName(), TOTAL_COLUMNS));
        invoiceText.append("\n");
        invoiceText.append(StringUtils.center(company.getAddress().getCompleteAddress(), TOTAL_COLUMNS));
        invoiceText.append("\n");
        invoiceText.append(StringUtils.center(company.getAddress().getCityAndState(), TOTAL_COLUMNS));
        invoiceText.append("\n\n");
        invoiceText.append("CNPJ: ").append(company.getCompanyNationalRegistration());
        invoiceText.append("\n");
        invoiceText.append("IE: ").append(company.getStateRegistration());
        invoiceText.append("\n");
        invoiceText.append("IM: ").append(company.getCityRegistration());
        invoiceText.append("\n");

        String dateTimeFormated = DATE_TIME_FORMATTER.format(dateTime);
        String invoiceCounterFormated = FIVE_DIGITS_DECIMAL_FORMAT.format(invoiceCounter);
        String operationOrderCounterFormated = FIVE_DIGITS_DECIMAL_FORMAT.format(operationOrderCounter);

        invoiceText.append(SEPARATOR);
        invoiceText.append("\n");
        invoiceText.append(StringUtils.rightPad(dateTimeFormated, oneThirdFromTotalColumns));
        invoiceText.append(
                StringUtils.center(String.format("%s: %s", "CCF", invoiceCounterFormated), oneThirdFromTotalColumns));
        invoiceText.append(StringUtils.leftPad(String.format("%s: %s", "COO", operationOrderCounterFormated),
                oneThirdFromTotalColumns));
        invoiceText.append("\n");

        invoiceText.append(SEPARATOR);
        invoiceText.append("\n");
        columnIndex = 0;
        invoiceText.append(StringUtils.rightPad("ITEM", sizeColumns[columnIndex++]));
        invoiceText.append(StringUtils.rightPad("CÓDIGO", sizeColumns[columnIndex++]));
        invoiceText.append(StringUtils.rightPad("DESCRIÇÃO", sizeColumns[columnIndex++]));
        invoiceText.append(StringUtils.leftPad("QTD", sizeColumns[columnIndex++]));
        invoiceText.append(StringUtils.leftPad("UN", sizeColumns[columnIndex++]));
        invoiceText.append(StringUtils.leftPad("VL.UNIT.", sizeColumns[columnIndex++]));
        invoiceText.append(StringUtils.leftPad("VL.TOTAL.", sizeColumns[columnIndex++]));
        invoiceText.append("\n");

        invoiceText.append(SEPARATOR);
        invoiceText.append("\n");
        for (Item item : items) {
            columnIndex = 0;
            String order = THREE_DIGITS_DECIMAL_FORMAT.format(item.getOrder());
            String id = FIVE_DIGITS_DECIMAL_FORMAT.format(item.getProduct().getId());
            String quantity = THREE_FRACTION_DIGITS_DECIMAL_FORMAT.format(item.getQuantity());
            String unitValue = TWO_FRACTION_DIGITS_DECIMAL_FORMAT.format(item.getProduct().getUnitValue());
            String totalValue = TWO_FRACTION_DIGITS_DECIMAL_FORMAT.format(item.getTotalValue());

            invoiceText.append(StringUtils.rightPad(order, sizeColumns[columnIndex++]));
            invoiceText.append(StringUtils.rightPad(id, sizeColumns[columnIndex++]));
            invoiceText.append(StringUtils.rightPad(item.getProduct().getName(), sizeColumns[columnIndex++]));
            invoiceText.append(StringUtils.leftPad(quantity, sizeColumns[columnIndex++]));
            invoiceText.append(StringUtils.leftPad(item.getProduct().getUnitMetric(), sizeColumns[columnIndex++]));
            invoiceText.append(StringUtils.leftPad(unitValue, sizeColumns[columnIndex++]));
            invoiceText.append(StringUtils.leftPad(totalValue, sizeColumns[columnIndex++]));
            invoiceText.append("\n");
        }

        invoiceText.append(SEPARATOR);
        invoiceText.append("\n");
        invoiceText.append(authenticationHash);

        return invoiceText.toString();
    }

    private BigDecimal getTotalValue() {
        return items.stream().map(Item::getTotalValue).reduce(BigDecimal.ZERO, (v1, v2) -> v1.add(v2));
    }
}
