package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "product_Style")
public class ProductStyle {

    @Id
    private String productStyleId;

    private String styleName;

//    private String styleLogo;

    private String styleImage;

    private String productSubSubCategoryId;

    private String productId;

    private Integer index;

}
