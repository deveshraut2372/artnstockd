package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "art_dimension_master")
public class ArtDimensionMaster {

    @Id
    private String artDimensionId;

    private String dimensionType;

    private Double widthInInches;

    private Double heightInInches;

    private Double widthInCentimeter;

    private Double heightInCentimeter;

    private Double widthInMillimeter;

    private Double heightInMillimeter;

    private Double widthInPixel;

    private Double heightInPixel;

    private String status;

}
