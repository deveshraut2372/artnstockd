package com.zplus.ArtnStockMongoDB.dto.req;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddCollectionArtAndAdminProductRequestDto {

    private String collectionId;

    List<String> adminArtProductIds;

    List<String> artIds;
}
