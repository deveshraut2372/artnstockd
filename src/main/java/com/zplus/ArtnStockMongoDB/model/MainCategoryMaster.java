package com.zplus.ArtnStockMongoDB.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "main_category_master")
public class MainCategoryMaster {

    @Id
    private String mainCategoryId;

    private String mainCategoryName;

    private String status;
    private String logo;
}
