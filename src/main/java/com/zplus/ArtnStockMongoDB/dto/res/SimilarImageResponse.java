package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.ImageMaster;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SimilarImageResponse {
    private ImageMaster imageMaster;
    private Double similarityScore;
    private String colorInfo; // Change this to String if you want to store the ColorInfo as a string

    public SimilarImageResponse(ImageMaster imageMaster, Double similarityScore, String colorInfo) {
        this.imageMaster = imageMaster;
        this.similarityScore = similarityScore;
        this.colorInfo = colorInfo;
    }

}
