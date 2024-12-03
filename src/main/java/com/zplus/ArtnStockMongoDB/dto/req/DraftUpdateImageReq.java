package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import lombok.Data;

import java.util.List;

@Data
public class DraftUpdateImageReq {

    private String draftId;
    private List<ImageMaster> imageMasters;
    private Boolean activeStatus;
    private String status;
//    private ImageOrientation imageOrientation;


}
