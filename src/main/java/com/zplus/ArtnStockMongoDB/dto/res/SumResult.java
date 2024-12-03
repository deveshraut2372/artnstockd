package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SumResult {
    private Double totalAmount;
    private Integer totalQuantity;

}
