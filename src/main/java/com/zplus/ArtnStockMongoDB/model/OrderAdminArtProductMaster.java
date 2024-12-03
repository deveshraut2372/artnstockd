package com.zplus.ArtnStockMongoDB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@ToString
@Data
@Getter
@Setter
@Document(collection = "order_admin_art_product_master")
public class OrderAdminArtProductMaster {

    @Id
    private String orderAdminArtProductId;
    private String cartId;
    private String adminArtproductName;
    private Double discount;
    private String size;
    private String cartAdminArtProductNo;
    private Integer quantity;
    private Double rate;
    private Double amount;
    private String stockStatus;
    private String description;
    private String status;
    private String cartAdminArtProductUniqueNo;
    private Date updatedDate;
    private String type;

    @DBRef
    @JsonIgnore
    private UserMaster userMaster;

    @DBRef
    @JsonIgnore
    private CartMaster cartMaster;

    //    @DBRef ArtProductMaster artProductMaster;
    @DBRef
    private AdminArtProductMaster adminArtProductMaster;

//    private SizeAndPrice sizeAndPrices;

    private ColorImagesModel images;

    private String imgUrl;

    private Double cartArtPrice;
}
