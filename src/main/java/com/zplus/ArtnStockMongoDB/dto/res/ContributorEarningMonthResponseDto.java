package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributorEarningMonthResponseDto {
    private Month month;
    private Integer itemSold;
    private Double earning;
    private Double productsPrice;
    private Double artPrint;
    private String paymentStatus;

}
