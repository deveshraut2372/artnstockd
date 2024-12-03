package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "common_master")
public class CommonMaster {

    @Id
    private String commonId;

    private String status;

    private Integer qty;

    private Double commonPrice;

    @DBRef
    private ProductMaster productMaster;

    @DBRef
    private ArtMaster artMaster;
}
