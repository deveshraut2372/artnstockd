package com.zplus.ArtnStockMongoDB.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@Data
@Document(collection = "subject_master")
public class SubjectMaster {
    @Id
    private String subjectId;

    private String image;

    private String subjectName;

    private String subjectDescription;

    private String subjectStatus;

    private String gridHeight;
    private String gridWidth;
    private String gridColumn;
    private String type;
    private Boolean artDropdown;

}
