package com.zplus.ArtnStockMongoDB.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@Document(collection = "Keywords_Master")
public class KeywordsMaster {

    @Id
    private String keywordId;
    private Set<String> keywords=new HashSet<>();

}
