package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AddArtRequestDto {
    private String fileManagerId;
    private String artId;
    private String adminArtProductId;

}
