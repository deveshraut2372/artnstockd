package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "limited_edition_master")
public class LimitedEditionMaster {

    @Id
    private String limitedEditionId;

    private String headingImage;

    private String mainImage;

    private String buttonText;

    private String type;

    private String contentStatus;

    private String status;

    private String newText;

    private String merchTextImg;

    private String bottomText;
}

