package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString
@Document(collection = "frame_master")
public class FrameMaster {

    @Id
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
    private Double price=0.0;
    private String stockStatus;
    private String size;
    private List<FrameColor> frameColor;

    private String frameImageUrl;
    private String frameThumbnailUrl;
    private String frameDrawingUrl;
// private List<SizeAndPrice> sizeAndPrices;

}
