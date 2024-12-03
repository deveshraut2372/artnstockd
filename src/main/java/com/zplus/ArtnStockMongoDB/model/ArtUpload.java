package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter@ToString
@Document(collection = "artUpload")
public class ArtUpload {
//    @Id
//    private String artUploadId;
    private String month;
    private List<DateMaster> dateMaster;

//    @DBRef
//    private UserMaster userMaster;

}
