package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalRefeEarningResDto {
    private Double Total;
    private List<ReferenceEarningSummaryResDto> referenceEarningSummaries=new ArrayList<>();

}
