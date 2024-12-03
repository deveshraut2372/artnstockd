package com.zplus.ArtnStockMongoDB.model;

import com.zplus.ArtnStockMongoDB.dto.res.GeoCityName;
import com.zplus.ArtnStockMongoDB.dto.res.GeoName;
import com.zplus.ArtnStockMongoDB.dto.res.GeoStateName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "country_master")
public class CountryMaster {
    @Id
    private String countryId;

    private String countryName;

    private String status;

    private String currencySymbol;

    private Double currency;

    private Boolean flag;

    private String countryPrefixCode;





}
