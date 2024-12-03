package com.zplus.ArtnStockMongoDB.dto.req;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArtProductSubSubCategoryReqDto {

    private String productSubSubCategoryId;

    private ArtProductStyleReqDto artProductStyleReqDto;


//    List<ArtProductStyleReqDto> artProductStyleReqDtoList;

}
