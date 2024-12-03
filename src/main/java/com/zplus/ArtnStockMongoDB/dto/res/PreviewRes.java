package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NegativeOrZero;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreviewRes {

    private Double zoom=1.0;

    private Double xAxis=0.0;

    private Double yAxis=0.0;

}
