package com.zplus.ArtnStockMongoDB.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "product_Color")
public class ProductColor {

    @Id
    private String productColorId;

    private String colorCode;

    private String colorType;

    private Integer quantity;

    private Double colorPrice;

    private String colorName;

    private String productImage;

    private String productStyleId;

    private String productId;

    //
    private String artproductImage;


    private Set<SizeAndPrice> sizeAndPrices;

    private Integer index;


}
