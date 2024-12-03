package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class OrientationMasterRequest {
    private String orientationId;
    private String shape;
    private String shapeStatus;
    private Double price;
    private String height;
    private String width;
    private Double contributorMarkupPercentage;
    private Integer artMarginPercentage;
    private Double artExpensesOne;
    private Double artExpensesTwo;
    private Double artExpensesThree;
    private Integer marginPercentage;

    private String printSize;
}
