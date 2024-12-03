package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.FrameOrientation;
import com.zplus.ArtnStockMongoDB.model.MatWiseMaster;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class FrameMatMasterReqDto {
    private String frameMatId;
    private String status;
    private String frameId;
    private Boolean frameFlag;
    private String frameColor;
    private String size;
    private String paper;
    private FrameOrientation frameOrientation;
    private List<MatWiseMaster> matWiseMasters;
    private String userId;
    private String artId;
}
