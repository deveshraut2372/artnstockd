package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddArtOrProductCollectionRequestDto {
    private String collectionId;
    private String artId;
    private String artProductId;

    private String adminArtProductId;
}
