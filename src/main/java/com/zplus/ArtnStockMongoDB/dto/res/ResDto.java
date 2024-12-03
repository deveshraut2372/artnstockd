package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class ResDto {
    private Double totalAmount=0.0;
    private Double tax=0.0;
    private Double taxAmount=0.0;
    private Double estimateAmount=0.0;
    private Double estimateShipping=0.0;
    private Integer totalQty=0;


    public ResDto() {

    }
}
