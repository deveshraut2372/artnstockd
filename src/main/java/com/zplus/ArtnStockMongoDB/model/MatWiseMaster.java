package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter@ToString
public class MatWiseMaster {
    private Boolean matFlag;
    private String matType;
    private String matColor;
    private String matWidth;

}
