package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddArtAndAdminProductRequestDto {

    private String fileManagerId;

    List<String> adminArtProductIds;

    List<String> artIds;

//    private String adminArtProductId;
//    private String artId;


}
