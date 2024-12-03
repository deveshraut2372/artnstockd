package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributorEarningDateResponseDto {
    private LocalDate date;
    private Integer itemSold;
    private Double earning;
    private Double productsPrice;
    private Double artPrint;
    private String paymentStatus;

}
