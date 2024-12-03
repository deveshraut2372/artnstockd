package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.CheckArtProductReq;
import com.zplus.ArtnStockMongoDB.dto.req.TempArtProductReq;
import com.zplus.ArtnStockMongoDB.dto.res.SeeAllProductRes;
import com.zplus.ArtnStockMongoDB.dto.res.TempArtProductMasterRes;
import com.zplus.ArtnStockMongoDB.dto.res.TempArtProductMasterResDto;
import com.zplus.ArtnStockMongoDB.dto.res.TempArtProductRes;
import com.zplus.ArtnStockMongoDB.model.TempArtProductMaster;

import java.util.List;

public interface TempArtProductService {
    Boolean createTempArtProductMaster(TempArtProductReq tempArtProductReq);

    List getActiveTempArtProductMaster();

    List getAllTempArtProductMaster();

    TempArtProductMaster getTempArtProductIdData(String tempArtProductId);

    List getAllTempArtProductIdByUserIdData(String userId);

    Boolean updateTempArtProductMaster(TempArtProductReq tempArtProductReq);

    Boolean DeleteTempArtProductIdData(String tempArtProductId);

    Boolean checkArtProductIsCreate(CheckArtProductReq checkArtProductReq);

    TempArtProductRes getArtProductIsCreate(CheckArtProductReq checkArtProductReq);

//    List<TempArtProductMaster> getTempProductByProductId(String productId);

    List<TempArtProductMaster> getTempProductByProductId(String productId, String productSubCategoryId, String userId);

    TempArtProductMasterRes getTempArtProductByProductIdAndSubCategory(String productSubSubCategoryId, String userId);

    Boolean getTempArtProductByProductIdAndSubCategoryExitOrNot(String productSubSubCategoryId, String userId);

    TempArtProductMasterResDto getTempArtProductByArtDetailsId(String artDetailsId, String userId);

    SeeAllProductRes getSeeAllProductsCount(String artDetailsId, String userId);

    TempArtProductMasterResDto getTempArtProductByArtDetailsIds(String artDetailsId, String userId, String productStyleId);
}
