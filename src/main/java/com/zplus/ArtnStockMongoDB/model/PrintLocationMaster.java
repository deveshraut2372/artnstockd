package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Document(collection = "Print_location_master")
public class PrintLocationMaster {

    @Id
    private String printLocationId;

    private String printLocationName;

    private String printLocationImage;

    private String productMainCategoryId;

    private String productSubCategoryId;

    private String productId;

    private Map<String,Object> previews;

    private double CanvasX;
    private double CanvasY;
    private int canvasSize;

}
