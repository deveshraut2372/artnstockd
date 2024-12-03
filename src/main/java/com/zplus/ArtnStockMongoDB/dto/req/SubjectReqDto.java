package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectReqDto {

    private String subjectId;
    private String image;
    private String subjectStatus;
    private String gridHeight;
    private String gridWidth;
    private String gridColumn;
    private String type;
    private String subjectName;
    private String subjectDescription;
    private Boolean artDropdown;



}
