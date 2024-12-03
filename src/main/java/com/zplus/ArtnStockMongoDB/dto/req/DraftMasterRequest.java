package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter@Setter
public class DraftMasterRequest {
    private String draftId;
    private List<String> imageId;
    private String userId;
}
