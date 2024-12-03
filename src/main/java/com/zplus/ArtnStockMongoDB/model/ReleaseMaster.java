package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Data
@ToString
@Document(collection = "release_master")
public class ReleaseMaster {

    @Id
    private String releaseId;
    private  String type;
    private String nameOfPerson;
    private String description;
    private List<String> release;
    private List<String> attachContent;
    @DBRef
    private UserMaster userMaster;

        private String releaseStatus;

    private Date date;
    private String fileUrl;
    private String fileName;
    private String fileSize;
    @DBRef
    private AddDetailsMaster addDetailsMaster;

    private String arProductNo;

}
