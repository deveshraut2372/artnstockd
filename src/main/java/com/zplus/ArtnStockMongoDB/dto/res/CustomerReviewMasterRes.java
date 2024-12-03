package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.dto.req.AdminReviewReply;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.CustomerReviewMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReviewMasterRes {

    private String customerReviewId;

    private String reviewMsg;

    private String status;

    private Integer reviewStar;

    private List<String> reviewImage;

    private Date date;

    private String reviewTitle;

    private CustomerReviewMaster customerReviewMaster;

    private ArtMaster artMaster;

    private List<AdminReviewReply> adminReviewReplies;

    private UserMaster userMaster;

    private Set<String> userIds;

    private Integer likeCount;

    private String reviewTime;

    public CustomerReviewMasterRes(String customerReviewId, String reviewMsg, String status, Integer reviewStar, List<String> reviewImage, Date date, String reviewTitle, CustomerReviewMaster customerReviewMaster, ArtMaster artMaster, List<AdminReviewReply> adminReviewReplies, UserMaster userMaster, Set<String> userIds, Integer likeCount) {
        this.customerReviewId = customerReviewId;
        this.reviewMsg = reviewMsg;
        this.status = status;
        this.reviewStar = reviewStar;
        this.reviewImage = reviewImage;
        this.date = date;
        this.reviewTitle = reviewTitle;
        this.customerReviewMaster = customerReviewMaster;
        this.artMaster = artMaster;
        this.adminReviewReplies = adminReviewReplies;
        this.userMaster = userMaster;
        this.userIds = userIds;
        this.likeCount = likeCount;
    }
}
