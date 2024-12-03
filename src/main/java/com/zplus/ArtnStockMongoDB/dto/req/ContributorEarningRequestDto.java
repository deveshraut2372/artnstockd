package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributorEarningRequestDto {
    private String artId;
//    private LocalDate date;
    private int quanitiy;
    private String orderStatus;
    private String productType;
    private String cartArtFrameMaster;
    private String cartProductMaster;
    private Double productPrice;
}
