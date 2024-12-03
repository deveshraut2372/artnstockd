package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.SocialMediaMasterReq;
import com.zplus.ArtnStockMongoDB.model.SocialMediaAdminMaster;

import java.util.List;

public interface SocialMediaMasterService {
    Boolean createLinkMaster(SocialMediaMasterReq socialMediaMasterReq);

    Boolean updateLinkMaster(SocialMediaMasterReq socialMediaMasterReq);

    List<SocialMediaAdminMaster> getActiveSocialMedia();

    List<SocialMediaAdminMaster> getAllSocialMedia();

    Boolean deleteSocialMedia(String socialMediaAdminId);
}
