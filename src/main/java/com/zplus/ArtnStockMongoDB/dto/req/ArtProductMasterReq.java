package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ColorImagesModel;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
public class ArtProductMasterReq {

    private String artProductId;
    private String artProductName;
    private String artId;
    private Date date;
    private String status;
    private String productId;
    private double CanvasX;
    private double CanvasY;
    private int canvasSize;
    private Integer qty;
    private String artProductUniqueNo;
    private SizeAndPrice sizeAndPrices;
    private ColorImagesModel images;
    private String adminArtProductId;
}
