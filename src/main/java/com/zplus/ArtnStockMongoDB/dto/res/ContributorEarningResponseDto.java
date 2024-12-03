package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContributorEarningResponseDto {
    private LocalDate date;
    private Integer itemSold;
    private Double earning;
    private Double productsPrice;
    private Double artPrint;
    private String paymentStatus;
    List<ContributorEarningMonthResponseDto> contributorEarningMonthResponseDtoList;

}
