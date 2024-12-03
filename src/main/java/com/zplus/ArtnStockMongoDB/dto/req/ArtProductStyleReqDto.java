package com.zplus.ArtnStockMongoDB.dto.req;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArtProductStyleReqDto {

    private String productStyleId;

    private List<ArtProductColorReq> artProductColorReqList;

}
