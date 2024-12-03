package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CountryMasterRequest {
    private String countryId;

    private String countryName;

    private String currencySymbol;

    private Double currency;

    private Boolean flag;

    private String status;

    private String countryPrefixCode;


}
