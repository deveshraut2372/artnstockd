package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@ToString
@Document(collection = "date_master")
public class DateMaster {

//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Id
    private String dateId;
    private Date date;
    private Integer count;

    @DBRef
    private UserMaster userMaster;
}
