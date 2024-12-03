package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PrintingSizeRequest {
    private String printingSizeId;
    private String height;
    private String width;
    private Double price;
    private String printingSizeStatus;
}
