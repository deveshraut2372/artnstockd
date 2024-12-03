package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "Release_File_Master")
public class ReleaseFileMaster {
    @Id
    private String releaseFileId;

    private String fileLink;

    private String fileSize;

    private String fileName;

    private String fileType;

    @DBRef
    private UserMaster userMaster;


}
