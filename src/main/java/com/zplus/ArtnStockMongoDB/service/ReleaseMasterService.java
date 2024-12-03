package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ReleaseMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.req.ReleaseSortReq;
import com.zplus.ArtnStockMongoDB.model.ReleaseMaster;

import java.util.List;

public interface ReleaseMasterService {
    Boolean createReleaseMaster(ReleaseMasterRequest releaseMasterRequest);

    Boolean updateReleaseMaster(ReleaseMasterRequest releaseMasterRequest);

    List getAllReleaseMaster();

    ReleaseMaster editReleaseMaster(String releaseId);

    List<ReleaseMaster> typeWiseReleaseMaster(String type);

    List<ReleaseMaster> getUserIdWiseReleaseMaster(String userId);

    List<ReleaseMaster> getReleasesByArtDetailsId(String artDetailsId);

    Boolean deleteByRelasesId(String releaseId);

    Boolean deleteReleasesByArtDetailsId(String artDetailsId);

    List<ReleaseMaster> getReleasesByUserIdAndType(String userId, String type);

    List<ReleaseMaster> getReleasesByFileName(String fileName);

    List<ReleaseMaster> getReleasesByFileNameAndSortType(String fileName, String sortType, String userId);

    List<ReleaseMaster> gerReleasesBySortTypeAndUserId(ReleaseSortReq releaseSortReq);



    int getReleasesCountByUserIdAndType(String userId, String type);

    List<ReleaseMaster> getReleasesByArtId(String artId);

    List<ReleaseMaster> getReleasesBySortTypeAndUserId(ReleaseSortReq releaseSortReq);
}
