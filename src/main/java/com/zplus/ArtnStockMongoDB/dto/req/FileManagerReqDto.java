package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter@Setter
public class FileManagerReqDto {

    private String fileManagerId;

    private String title;

    private Date updatedDate;

//    private Integer count;

    private String status;

    private String category;

    private String userId;

    private List<String> artId;
    private List<String> artProductId;

    private List<String>  adminArtProductId;

    private String coverImage;
}
