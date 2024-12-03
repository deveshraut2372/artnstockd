package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "frequently_asked_questions_master")
public class FrequentlyAskedQuestionsMaster {

    @Id
    private String faqId;

    private String question;

    private String answer;

    private String type;

    private String status;


}
