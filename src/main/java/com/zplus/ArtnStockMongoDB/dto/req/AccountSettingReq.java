package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.CountryMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSettingReq {

    private String accountSettingId;
    private String cardNo;
    private String cardName;
    private String expMonth;
    private String expYear;
    private String cvc;
    private String status;
    private String countryId;
    private String address1;
    private String address2;
    private String city;
    private String zipCode;
    private String state;
    private String phoneNo;
    private String userId;
}
