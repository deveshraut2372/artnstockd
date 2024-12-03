package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "printing_size_master")
public class PrintingSizeMaster {
    @Id
    private String printingSizeId;
    private String height;
    private String width;
    private Double price;
    private String printingSizeStatus;

}
