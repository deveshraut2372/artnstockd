package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = " Art_Details_Master")
public class AddDetailsMaster {

    @Id
    private String artDetailsId;

    private String artName;

    private List<String> commercialUser;

    private List<String> typeOfContent;

    private List<String> referenceFile;

    private String description;

    private List<String> keywords;

    //   @DBRef
//    private String artMedium;

    @DBRef
    private  MediumMaster mediumMaster=new MediumMaster();


    @DBRef
    private SubjectMaster subjectMaster=new SubjectMaster();

//    private String subjectId;

    @DBRef
    private StyleMaster styleMaster=new StyleMaster();
//    private String styleId;

    private String image;

    @DBRef
    private UserMaster userMaster;

    @DBRef
    private ImageMaster imageMaster;

    private List<String> releases;

    private Double price;

    private String notes;


//    @DBRef
//    private DraftMaster draftMaster;

    private Map<String,Object> previews;


}
