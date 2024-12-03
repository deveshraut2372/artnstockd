package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class LimitedEditionReqDto {
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
