package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingAddressReq {

    private String countryName;
    private String addressLine1;
    private String addressLine2;
    private String cityName;
    private String zipCode;
    private String stateName;
    private String phoneNo;
    private String addressStatus;

    private String countryId;
    private String stateId;
    private String cityId;
    private String type;


}
