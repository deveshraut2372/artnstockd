package com.zplus.ArtnStockMongoDB.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "Contributor_Type_Master")
public class ContributorTypeMaster {

    @Id
    private String contributorTypeId;

    private String contributorTypeName;

    private String status;

}
