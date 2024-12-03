package com.zplus.ArtnStockMongoDB.model;

import com.zplus.ArtnStockMongoDB.dto.req.MatWidth;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "mat_master")
public class MatMaster {
    @Id
    private String matId;
    private String matType;
    private String status;
    private Double price=0.0;
    private List<FrameColor> frameColor;
//    private List<String> matWidth;
//    private MatWidth matWidth;
private List<MatWidth> matWidth;


}
