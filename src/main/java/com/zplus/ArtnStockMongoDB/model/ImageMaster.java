package com.zplus.ArtnStockMongoDB.model;

import com.zplus.ArtnStockMongoDB.dto.res.ColorInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Document(collection = "image_master")
public class ImageMaster {

    @Id
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
    @Field("colorInfos")
    private List<ColorInfo> colorInfos;

    @DBRef
    private UserMaster userMaster;

    private Boolean activeStatus=false;

    @Field
    private String status;

    private Date date;

    private Double zoom=1.0;

    private Double xAxis=0.0;

    private Double yAxis=0.0;

    private String thumbnailUrl;
    

//    private ArtDetailsMasterReq artDetailsMasterReq;

    private Map<String,Object> previews;


}
