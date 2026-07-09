package com.paula.click2buy.payments.endpoints.dtos;

import java.util.List;

public class BacenResponseDTO {

    private List<QuotationDTO> value;

    public List<QuotationDTO> getValue() {
        return value;
    }

    public void setValue(List<QuotationDTO> value) {
        this.value = value;
    }
}
