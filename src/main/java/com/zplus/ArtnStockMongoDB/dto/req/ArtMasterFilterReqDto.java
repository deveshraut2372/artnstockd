package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@Data
@ToString
public class ArtMasterFilterReqDto {

    private String size;
    private String orientation;
    private Double price;
    private String userFirstName;//contributorName
    private String styleName;
    private String subjectName;
    private String keyword;
    private String colorCode;
    private Double minPrice;
    private Double maxPrice;


}
