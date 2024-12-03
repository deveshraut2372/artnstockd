package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.MatMaster;
import com.zplus.ArtnStockMongoDB.model.Orientation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@AllArgsConstructor
public class FrameMatMasterResDto {
    private String frameMatId;
    private String status;
    private String frameId;
    private Boolean frameFlag;
    private String frameColor;
    private String size;
    private String paper;
    private Orientation orientation;
    private MatMaster matMaster;
    private String userId;
    private String artId;

    public FrameMatMasterResDto() {

    }
}
