package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TotalSummary {
    private double totalAmount;
    private int totalQty;

}
