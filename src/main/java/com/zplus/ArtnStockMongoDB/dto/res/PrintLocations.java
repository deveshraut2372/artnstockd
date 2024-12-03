package com.zplus.ArtnStockMongoDB.dto.res;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrintLocations {

    private String productSubSubCategoryId;

    private String printLocationName;

    private String printLocationLogo;

    private String printLocationImage;

    private double CanvasX;

    private double CanvasY;

    private int canvasSize;

}
