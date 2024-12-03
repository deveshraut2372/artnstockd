package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "style_master")
public class StyleMaster {
    @Id
    private String styleId;

    private String name;

    private String image;

    private String status;

    private boolean artDropdown;

}
