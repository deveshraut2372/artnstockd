package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.DeleteImageReq;
import com.zplus.ArtnStockMongoDB.dto.req.DraftMasterRequest;
import com.zplus.ArtnStockMongoDB.model.DraftMaster;

import java.util.List;

public interface DraftMasterService {
    Boolean createDraftMaster(DraftMasterRequest draftMasterRequest);

    Boolean updateDraftMaster(DraftMasterRequest draftMasterRequest);

    DraftMaster editDraftMaster(String draftId);

    List getDraftMasterList();

    List getContributorWiseDraftMasterList(String userId);

    DraftMaster getDraftMasterByStatusAndActiveStatus(String status, Boolean activeStatus, String userId);

    DraftMaster getDraftMasterByStatus(String status, String userId);

    Boolean deleteDraftByDraftId(String draftId);

    Boolean deleteImage(DeleteImageReq deleteImageReq);

    Integer getDraftMasterByStatusAndActiveStatusCount(String status, Boolean activeStatus, String userId);
}
