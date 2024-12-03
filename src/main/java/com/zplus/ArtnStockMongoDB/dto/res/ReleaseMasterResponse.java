package com.zplus.ArtnStockMongoDB.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter@AllArgsConstructor
public class ReleaseMasterResponse {
    private String releaseId;
    private  String type;
    private String nameOfPerson;
    private String description;
    private List<String> release;
    private List<String> attachContent;
    private String userId;
}
