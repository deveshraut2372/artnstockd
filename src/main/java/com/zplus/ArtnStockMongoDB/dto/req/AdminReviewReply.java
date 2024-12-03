package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter@Setter
public class AdminReviewReply {

    private String adminReviewReply;
    private Date date;
}
