//package com.zplus.ArtnStockMongoDB.model;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//@Getter
//@Setter
//@Data
//public class CollectionUserArtMaster {
//@Document(collection = " CollectionUserArtMaster")
//
//    @Id
//    private String collectionUserArtId;
//
//    private String artId;
//    private String artName;
//    private String image;
//    private Double price;
//    private String status;
//    private String orientation;
//    private String arProductNo;
//    private String size;
//    private String type="art";
//    private String description;
//    private Double stock;
//    private String artMedium;
//    private String stockStatus;
//    private String height;
//    private String width;
//    private String previewImage;
//    private String  notes;
//    private Date submittedDate;
//    private Date reviewData;
//    private Date approveDate;
//    private Double finalArtPrice;
//    private Double discountPercentage;
//    //List of Array
//    private List<String> commercialUser;
//    private List<String> typeOfContent;
//    private List<String> referenceFile;
//    private List<String> keywords;
//    private List<String> releases;
//
//    //Relation
//    @DBRef
//    private UserMaster userMaster;
//
//    private StyleMaster styleMaster;
//    private SubjectMaster subjectMaster;
//    private ImageMaster imageMaster;
//    private MediumMaster mediumMaster;
//
//    private Boolean festiveOffer=false;
//
//    @DBRef
//    private AddDetailsMaster addDetailsMaster;
//
//    private List<ReleaseMaster> releaseMasterList=new ArrayList<>();
//
////    public ArtMaster() {
////
////    }
//
//    private Date updatedDate;
//
//    private String fileManagerId;
//
//    private String collectionId;
//
//}
