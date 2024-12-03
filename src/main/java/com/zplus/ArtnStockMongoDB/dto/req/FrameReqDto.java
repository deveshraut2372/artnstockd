package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.FrameColor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class FrameReqDto {

    private String frameId;
    private String frameName;
    private String frameDescription;
    private String frameProductNo;// unique no generate
    private String frameType;
    private String width;
    private String depth;
    private String finish;
    private String style;
    private String glassType;
    private String status;
    private String frameMaterial;
    private Double price;
    private String stockStatus;
    private List<FrameColor> frameColor;
    private String frameImageUrl;
    private String frameThumbnailUrl;
    private String frameDrawingUrl;

}
