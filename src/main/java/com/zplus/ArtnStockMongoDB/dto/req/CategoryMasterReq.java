package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ProductStyle;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CategoryMasterReq {

    private String categoryId;

    private String categoryName;

    private String status;

    private LocalDate localDate;

    private List<ProductStyle> productStyles;


}
