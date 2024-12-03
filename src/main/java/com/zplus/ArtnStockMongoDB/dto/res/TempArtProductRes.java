package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TempArtProductRes {
    private String tempArtProductId;

    private String tempArtProductName;

    private Date date;

    private String status;

    private String image;

    private List<SizeAndPrice> sizeAndPrices;

    private Integer qty;

    private String artProductUniqueNo;

    private double CanvasX;
    private double CanvasY;
    private int canvasSize;

    //    Relation
    private String userId;

//    @DBRef
//    private ArtMaster artMaster;

    private String productId;

    private List<ColorImagesModel> images;

//    private  String  approveStatus;

    private String artDetailsId;
}
