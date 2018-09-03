package br.com.fiap.roupas.builder;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextPDFBuilderTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-hh-mm-ss-SSS");

    @Test
    public void buildTest() {
        String fileName = DATE_TIME_FORMATTER.format(LocalDateTime.now());
        File file = new File(String.format("./temp/%s.pdf", fileName));

        TextPDFBuilder textPDFBuilder = new TextPDFBuilder();
        textPDFBuilder.setText(getText());
        textPDFBuilder.setFile(file);

        textPDFBuilder.build();
    }

    private String getText() {
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
}
