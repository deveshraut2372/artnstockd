package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ColorImagesModel;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import lombok.Data;

import java.util.Date;

@Data
public class AdminArtProductMasterReq {

    private String adminArtProductId;
    private String adminartProductName;
    private String artId;
    private Date date;
    private String status;
    private String productId;
    private double CanvasX;
    private double CanvasY;
    private int canvasSize;
    private Integer qty;
    private String adminArtProductUniqueNo;
    private SizeAndPrice sizeAndPrices;
    private ColorImagesModel images;
//    private String adminArtProductId;
}
