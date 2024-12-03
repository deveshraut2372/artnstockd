package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class PrintingMaterialServiceReqDto {
    private String printingMaterialId;
    private String printingMaterialName;
    private Double printingMaterialPrice;
    private String printingMaterialStatus;
    private String description;
    private Boolean checked;
}
