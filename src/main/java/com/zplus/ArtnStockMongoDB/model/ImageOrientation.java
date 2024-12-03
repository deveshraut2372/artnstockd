package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter@Setter
public class ImageOrientation {
    private String squareUrl;
    private String verticalUrl;
    private String horizontalUrl;
    private String squareBCUrl;
    private String verticalBCUrl;
    private String horizontalBCUrl;

}
