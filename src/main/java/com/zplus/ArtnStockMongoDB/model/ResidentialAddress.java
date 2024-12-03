package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidentialAddress {

    private String countryName;
    private String addressLine1;
    private String addressLine2;
    private String cityName;
    private String postalCode;
    private String stateName;
    private String phoneNo;

    private String countryId;
    private String stateId;
    private String cityId;

}
