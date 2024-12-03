package com.zplus.ArtnStockMongoDB.dto.res;

import com.zplus.ArtnStockMongoDB.model.CartAdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.CartMaster;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CartAdminArtProductRes {

    private Boolean flag;

    private CartAdminArtProductMaster cartAdminArtProductMaster;

    private CartMaster cartMaster;
}
