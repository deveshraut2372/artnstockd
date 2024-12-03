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
@Document(collection = "printing_material_master")
public class PrintingMaterialMaster {
    @Id
    private String printingMaterialId;
    private String printingMaterialName;
    private Double printingMaterialPrice=0.0;
    private String printingMaterialStatus;
    private String description;
    private Boolean checked;

}
