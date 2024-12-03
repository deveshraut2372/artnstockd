package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter@Setter@AllArgsConstructor
public class RecentlyViewResponse {
    private String artId;
    private String artName;
    private String image;
    private Double price;
    private String status;
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
    private String  notes;
    private Date submittedDate;
    private Date reviewData;
    private Date approveDate;
    private Date viewedAt;


    private List<String> commercialUser;
    private List<String> typeOfContent;
    private List<String> referenceFile;
    private List<String> keywords;

    private String userId;
}
