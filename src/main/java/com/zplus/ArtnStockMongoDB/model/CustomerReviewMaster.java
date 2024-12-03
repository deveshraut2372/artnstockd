package com.zplus.ArtnStockMongoDB.model;

import com.zplus.ArtnStockMongoDB.dto.req.AdminReviewReply;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Document(collection = "customer_review_master")
public class CustomerReviewMaster {

    @Id
    private String customerReviewId;

    private String reviewMsg;

    private String status;

    private Integer reviewStar;

    private List<String> reviewImage;

    private Date date;

    private String reviewTitle;
    @DBRef
    private CustomerReviewMaster customerReviewMaster;
    @DBRef
    private ArtMaster artMaster;

    private List<AdminReviewReply> adminReviewReplies;

//    @DBRef
//    private ProductMaster productMaster;

//    @DBRef
//    private ArtFrameMaster artFrameMaster;
//    @DBRef
//    private ArtProductMaster artProductMaster;
    @DBRef
    private UserMaster userMaster;

    private Set<String> userIds;

    private Integer likeCount;

}
