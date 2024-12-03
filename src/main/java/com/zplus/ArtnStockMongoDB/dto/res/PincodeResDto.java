package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PincodeResDto {
    
    private String pinCodeId;

    private Integer pinCode;

    private String status;

    private String countryId;

    private String countryName;



}
