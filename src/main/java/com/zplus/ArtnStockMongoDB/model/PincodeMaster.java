package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "pincode_master")
public class PincodeMaster {
    @Id
    private String pinCodeId;

    private Integer pinCode;

    private String status;

    @DBRef
    private CountryMaster countryMaster;
}





