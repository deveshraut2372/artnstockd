package com.zplus.ArtnStockMongoDB.model;

import com.zplus.ArtnStockMongoDB.dto.res.GeoCityName;
import com.zplus.ArtnStockMongoDB.dto.res.GeoName;
import com.zplus.ArtnStockMongoDB.dto.res.GeoStateName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Accont_Setting_master")
public class AccountSettingMaster {

    @Id
    private String accountSettingId;
    private String cardNo;
    private String cardName;
    private String expMonth;
    private String expYear;
    private String cvc;
    private String status;
    @DBRef
    private CountryMaster countryMaster;
    private String address1;
    private String address2;
    private String city;
    private String zipCode;
    private String state;
    private String phoneNo;
    @DBRef
    private UserMaster userMaster;


    List<GeoName> CountryList=new ArrayList<>();

    List<GeoStateName> statesList=new ArrayList<>();

    List<GeoCityName> citysList=new ArrayList<>();



}
