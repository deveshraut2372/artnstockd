package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class FrequentlyAskedQuestionsReqDto {
    private String faqId;

    private String question;

    private String answer;

    private String type;

    private String status;

}
