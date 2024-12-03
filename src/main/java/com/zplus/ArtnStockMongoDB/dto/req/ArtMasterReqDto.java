package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ArtMasterReqDto {
    private String artId;
    private String artName;
    private String image;
    private Double price;
//    private String status;
    private String orientation;
    private String arProductNo;
    private String size;
    private String type;
    private String description;
    private Double stock;
    private String artMedium;
    private String stockStatus;
    private String height;
    private String width;
    private String previewImage;
    private String notes;
    private Date submittedDate;
    private Date reviewData;
    private Date approveDate;
    private Date viewedAt;

    private List<String> commercialUser;
    private List<String> typeOfContent;
    private List<String> referenceFile;
    private List<String> keywords;
    private String imageId;
//    private List<String> releases;

    private String userId;
    private String styleId;
    private String subjectId;
    private String artDetailsId;

    private ImageOrientation imageOrientation;



}
