package com.zplus.ArtnStockMongoDB.dto.req;


import com.zplus.ArtnStockMongoDB.model.ProductColor;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Product_Style")
public class ProductStyleReq {

    @Id
    private String productStyleId;

    private String styleName;

    private String image;

    private String status;

//    private Set<ProductColor> productColors;
//    private Set<ProductColor> standaredColors;
//
//    private Set<ProductColor> neonColors;

    private String productSubSubCategoryId;

}
