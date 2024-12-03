package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class ReviewReplyRequestDto {
    private String customerReviewId;
    private List<AdminReviewReply> adminReviewReplies;
}
