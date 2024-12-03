package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ImageOrientation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddDetailsMasterReq {

    private String artDetailsId;
    private String artName;
    private List<String> commercialUser;
    private List<String> typeOfContent;
    private List<String> referenceFile;
    private String description;
    private List<String> keywords;
    //   @DBRef
//    private String artMedium;

    private String mediumId;
    private String subjectId;
    private String styleId;
//    private String image;
    private String userId;
    private String imageId;
    private List<String> releases;
    private Double price;
    private String notes;

    private String draftId;

    public AddDetailsMasterReq(String artName, List<String> commercialUser, List<String> typeOfContent, List<String> referenceFile, String description, List<String> keywords, List<String> releases, Double price, String notes) {
        this.artName = artName;
        this.commercialUser = commercialUser;
        this.typeOfContent = typeOfContent;
        this.referenceFile = referenceFile;
        this.description = description;
        this.keywords = keywords;
        this.releases = releases;
        this.price = price;
        this.notes = notes;
    }


    private ImageOrientation imageOrientation;

    private Double zoom=1.0;

    private Double xAxis=0.0;

    private Double yAxis=0.0;

    private Map<String,Object> previews;



}
