package com.zplus.ArtnStockMongoDB.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Category_Master")
public class CategoryMaster {

    @Id
    private String categoryId;

    private String categoryName;

    private String status;

    private LocalDate localDate;

    private List<ProductStyle> productStyles;

}
