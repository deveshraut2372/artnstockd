package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.DraftDeleteImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.DraftUpdateImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateImageReq;
import com.zplus.ArtnStockMongoDB.model.ImageMaster;

public interface ImageService {
    Boolean updateImageMaster(DraftUpdateImageReq draftUpdateImageReq);

    Boolean deleteImages(DraftDeleteImageReq draftDeleteImageReq);

    Boolean updateImg(ImageMaster imageMaster);
}
