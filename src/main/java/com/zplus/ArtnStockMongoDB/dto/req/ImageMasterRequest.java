package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.dto.res.ColorInfo;
import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ImageMasterRequest {
    private String imageId;
    private String publicId;
    private Integer height;
    private Integer width;
    private Integer bytes;
    private String secureUrl;
    private List<String> suggestedKeywordList;
    private String pHashId;
    private String type;
    private ImageOrientation imageOrientation;
    private List<ColorInfo> color;
}
