package com.zplus.ArtnStockMongoDB.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter@ToString
@Document(collection = "sizeAndPrice")
public class SizeAndPrice {

    @Id
    private String SizeAndPriceId;
    private String size;
    private Double basePrice;
    private Double sellPrice;
    private String sizeName;

    //change new
    private Double markup;

    private String productColorId;

    private String productId;

    private Boolean defaultValue;

}
