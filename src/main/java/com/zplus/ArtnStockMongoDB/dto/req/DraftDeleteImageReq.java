package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class DraftDeleteImageReq {

    private String draftId;
    private List<ImageMasterIds> imageMasterIds;

}
