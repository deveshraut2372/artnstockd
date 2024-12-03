package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "wishlist_master")
public class WishListMaster {

    @Id
    private String wishListId;

    private Date wishListDate;
    private String wishListStatus;

    @DBRef
    private UserMaster userMaster;
    @DBRef
    private ArtMaster artMaster;
    @DBRef
    private ArtProductMaster artProductMaster;
    @DBRef
    private AdminArtProductMaster adminArtProductMaster;

}
