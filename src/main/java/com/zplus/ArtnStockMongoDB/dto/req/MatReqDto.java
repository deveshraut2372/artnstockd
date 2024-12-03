package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.FrameColor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter
public class MatReqDto {
    private String matId;
    private String matType;
    private String status;
    private Double price;
    private List<FrameColor> color;
//    private List<String> matWidth;
//    private MatWidth matWidth;
private List<MatWidth> matWidth;

}
