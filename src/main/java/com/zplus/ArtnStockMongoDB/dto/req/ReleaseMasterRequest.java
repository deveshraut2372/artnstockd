package com.zplus.ArtnStockMongoDB.dto.req;

import com.zplus.ArtnStockMongoDB.model.ReleaseFileMaster;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter@Setter
@ToString
public class ReleaseMasterRequest {
    private String releaseId;
    private  String type;
    private String nameOfPerson;
    private String description;

//    private List<String> release;
//    private List<String> attachContent;
    private String userId;
    private String releaseStatus;
    private String fileUrl;
    private String fileName;
    private String fileSize;
    private String artDetailsId;

}
