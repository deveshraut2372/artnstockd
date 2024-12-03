package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
@Getter
@Setter
@Data
@ToString
@Document(collection = "recently_search_master")
public class RecentlySearchMaster {
    @Id
    private String recentlySearchId;

    private Date date;

    private String text;

    @DBRef
    private UserMaster userMaster;
}
