package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.FrameColor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class CartMatReq {
    private String matId;
    private String matType;
    private String status;
    private Double price;
     private String color;
    private String matWidth;
}
