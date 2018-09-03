package br.com.fiap.roupas.entity;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class Address {

    private String address;
    private String number;
    private String complement;
    private String city;
    private String state;

    public String getCompleteAddress() {
        String completeAddress = "";
        completeAddress = append(completeAddress, address);
        completeAddress = append(completeAddress, number);
        completeAddress = append(completeAddress, complement);
        return completeAddress;
    }

    public String getCityAndState() {
        String cityAndState = "";
        cityAndState = append(cityAndState, city);
        cityAndState = append(cityAndState, state);
        return cityAndState;
    }

    private String append(String completeAddress, String toAppend) {
        String newCompleteAddress = "";

        if (StringUtils.isNotBlank(completeAddress)) {
            newCompleteAddress = completeAddress + " - ";
        }

        if (StringUtils.isNotBlank(toAppend)) {
            newCompleteAddress = newCompleteAddress + toAppend;
        }

        return newCompleteAddress;
    }

}
