package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ShippingAddress {

    @Id
    private String shippingAddressId;
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

    private String firstName;
    private String lastName;
    private Boolean defaultType;



}
