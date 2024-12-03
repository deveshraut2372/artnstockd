package com.zplus.ArtnStockMongoDB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@ToString
@Getter
@Setter
@Document(collection = "cartproduct_master")
public class CartProductMaster {
    @Id
    private String cartProductId;
    private String cartId;
    private String productName;
    private Double discount;
    private String size;
    private String cartProductNo;
    private Integer quantity;
    private Double rate;
    private Double amount;
    private String stockStatus;
    private String description;
    private String status;
    private String cartProductUniqueNo;
    private Date updatedDate;
    private String type;


    @DBRef
    @JsonIgnore
    private UserMaster userMaster;

    @DBRef
    @JsonIgnore
    private CartMaster cartMaster;

    @DBRef ArtProductMaster artProductMaster;

    @DBRef
    private AdminArtProductMaster adminArtProductMaster;


}
