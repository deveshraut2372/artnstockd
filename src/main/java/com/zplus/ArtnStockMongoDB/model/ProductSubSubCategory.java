package com.zplus.ArtnStockMongoDB.model;

//import com.zplus.ArtnStockMongoDB.dto.req.ProductStyle;
import com.zplus.ArtnStockMongoDB.dto.res.PrintLocations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Product_Sub_Sub_Category")
public class ProductSubSubCategory {

    @Id
    private String productSubSubCategoryId;

    private String productSubSubCategoryName;

    private String status;

    private LocalDate date;

//    private List<PrintLocations> printLocationsList;

    private String productSubCategoryId;

    private String productMainCategoryId;

    private String productId;

    private Integer index;

}
