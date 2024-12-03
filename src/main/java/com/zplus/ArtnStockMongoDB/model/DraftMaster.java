package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Document(collection = "draft_master")
public class DraftMaster {
    @Id
    private String draftId;
    private LocalDate date;
    @Field("imageMaster")
    private List<ImageMaster> imageMaster;

    @DBRef
    private UserMaster userMaster;



}
