package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter@Setter
public class ColorResponse {

    private String height;
    private String width;
    private String format;
    private String resourceType;
    private String createdAt;
    private Double bytes;
    private String secureUrl;
    private String originalFilename;
    private List<ColorInfo> colorlist;
    private String publicId;
    private String phashId;
    private Integer version;
    private ImageOrientation orientation;
    private String imageId;
    private String type;
    private List<String> suggestedKeywordList;

    private String thumbnailUrl;

}
