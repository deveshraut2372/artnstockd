package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Document(collection = "file_upload_limit_per_week_master")
public class FileUploadLimitPerWeekMaster {
    @Id
    private String fileUploadLimitPerWeekId;

    private Double art;

    private Double photos;

    private Double footage;

    private Double music;

    private Double templates;

    private Double approvalPercentageFrom;

    private Double approvalPercentageTo;

//    private Double approvalPercentage;
}
