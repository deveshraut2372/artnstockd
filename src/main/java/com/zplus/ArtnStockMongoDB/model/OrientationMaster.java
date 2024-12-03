package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "orientation_master")
public class OrientationMaster {
    @Id
    private String orientationId;
    private String shape;
    private String shapeStatus;
    private Double price=0.0;  // price // markup
    private String height;
    private String width;
    private Double contributorMarkupPercentage;
    private Double contributorCalculatedPrice;
    private Integer artMarginPercentage;
    private Double marginAmount;
    private Double artExpensesOne;
    private Double artExpensesTwo;
    private Double artExpensesThree;
    private Double subTotalExpenses;
    private Integer marginPercentage;
    private Double totalExpenses;
    private Double basePrice;
    private Double sellPrice;

    private String printSize;



}
