package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.CartArtFrameMaster;
import com.zplus.ArtnStockMongoDB.model.CartMaster;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CartArtSaveResponse {
    private Boolean flag;
    private CartArtFrameMaster cartArtFrameMaster;
    private CartMaster cartMaster;
}
