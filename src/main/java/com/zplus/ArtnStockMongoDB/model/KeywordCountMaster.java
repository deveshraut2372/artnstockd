package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "keyword_count_master")
@ToString
public class KeywordCountMaster {

    @Id
    private String keywordCountId;

    private String keyword;

    private Integer count;

//    @DBRef
//    private ArtMaster artMaster;
}
