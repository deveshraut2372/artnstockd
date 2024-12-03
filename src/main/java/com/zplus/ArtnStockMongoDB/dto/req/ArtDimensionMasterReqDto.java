package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ArtDimensionMasterReqDto {
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
