package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class PincodeMasterRequest {
    private String pinCodeId;
    private Integer pinCode;
    private String status;
    private String countryId;

}
