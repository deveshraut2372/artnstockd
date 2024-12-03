package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.CollectionCountRes;
import com.zplus.ArtnStockMongoDB.dto.res.CollectionMasterRes;
import com.zplus.ArtnStockMongoDB.dto.res.CollectionRes;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.CollectionMaster;

import java.util.List;

public interface CollectionMasterService {

    Boolean createCollectionMaster(CollectionMasterReqDto collectionMasterReqDto);

    Boolean updateCollectionMaster(CollectionMasterReqDto collectionMasterReqDto);

    List getAllCollectionMaster();

    CollectionMaster editCollectionMaster(String collectionId);

    List getActiveCollectionMaster();

    List<CollectionMasterRes> getUserIdWiseCollectionMasterList(String userId);

    List<ArtMaster> getCollectionIdWiseArtList(String collectionId);

    List<CollectionRes> getUserIdWiseCollectionData(String userId);

    Boolean CollectionIdWiseAddArt(AddArtInCollectionRequestDto addArtInCollectionRequestDto);

    Boolean updateIdWiseCollectionTitle(UpdateCollectionTitleReqDto updateCollectionTitleReqDto);

    Boolean deleteCollection(String collectionId);

    List CollectionFilter(FilterReqDto filterReqDto);

    Boolean addArtOrProductInCollection(AddArtOrProductCollectionRequestDto addArtOrProductCollectionRequestDto);

        Boolean CollectionIdWiseAddArtAndAdminProduct(AddCollectionArtAndAdminProductRequestDto addCollectionArtAndAdminProductRequestDto);

    List<CollectionMaster> getCollectionsByUserIdAndSort(CollectionSortRequest collectionSortRequest);

    List getCollectionByListSort(CollectionOrderReq collectionOrderReq);

    Boolean deleteCollectionIdWiseAddArtAndAdminProduct(CollectionAddArtAndAdminProductRequestDto collectionAddArtAndAdminProductRequestDto);

    Boolean updateCollectionCoverImage(UpdateCollectionCoverImageReq updateCollectionCoverImageReq);

    Boolean CollectionIdWiseAddArtAndAdminProduct1(AddCollArtAndAdminProductRequest addCollArtAndAdminProductRequest);

    List getAllCollectionsArtAndAdmiAProducts(CollectionSortReq collectionSortReq);

    List getCollectionListByUserIdAndSort(String collectionId);

    List getAllCollectionsArtAndAdminProducts(CollectionSortReq collectionSortReq);

    List<CollectionMasterRes> getCollectionsByUserIdAndSor(CollectionSortRequest collectionSortRequest);

    List getCollectionResponseByCollectionIdWise(CollectionOrderReq collectionOrderReq);

    CollectionCountRes CollectionCountRes(String userId);
}
