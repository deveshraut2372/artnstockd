package com.zplus.ArtnStockMongoDB.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Medium_Master")
public class MediumMaster {

    @Id
    private String mediumId;

    private Boolean artDropDown;

    private String mediumImage;

    private String mediumDescription;

    private String mediumType;

    private String mediumStatus;


}
