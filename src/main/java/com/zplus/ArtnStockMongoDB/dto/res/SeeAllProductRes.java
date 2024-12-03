package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeeAllProductRes {

    private Integer totalProducts=0;

    private Integer activateProducts=0;

    private Integer deActivateProducts=0;

    private Integer remainingProducts=0;

}
