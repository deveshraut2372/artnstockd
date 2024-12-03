package com.zplus.ArtnStockMongoDB.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterFilterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ArtMasterUpdateReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.SortRequest;
import com.zplus.ArtnStockMongoDB.dto.res.ApprovedRatioRes;
import com.zplus.ArtnStockMongoDB.dto.res.ArtMasterRes;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.ContributorArtMarkupMaster;
import com.zplus.ArtnStockMongoDB.model.KeywordCountMaster;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Set;

public interface ArtMasterService {
    ArtMasterRes createArtMaster(ArtMasterReqDto artMasterReqDto);

    Boolean updateArtMaster(ArtMasterReqDto artMasterReqDto);

    List getActiveArtMasterList();

    List getUserIdWiseArtMasterList(String userId);

    List getStyleIdIdWiseStyleMaster(String styleId);

    List ArtMasterFilter(ArtMasterFilterReqDto artMasterFilterReqDto);

    Long getByCountUser();

    ArtMaster editArtMaster(String artId);

    List<ArtMaster> searchByText(String searchText);
    
    List getSubjectIdWiseSubjectMaster(String subjectId);

    List<ArtMaster> searchTextByArtName(String searchText);

    Boolean getArtIdWiseChangeStatus(String artId);

    Boolean getUserIdWiseApprovedPerChange(String userId);

    List<ArtMaster> searchTextByUserFirstName(String userFirstName);

    List<ArtMaster> getSimilarImage(String image);

    List<ArtMaster> FindSimilarImageList(String image) throws JsonProcessingException;

    List subjectNameWiseArtList(String subjectName);

    List styleNameWiseArtList(String name);

    List ArtFilter(ArtMasterFilterReqDto artMasterFilterReqDto, String type, String text);

    List getKeywordMasterList();

    KeywordCountMaster getKeywordCountIdWiseData(String keywordCountId);

    List<ArtMaster> getUserIdAndStatusWiseUserMaster(String userId, String status);

    Boolean updateUserIdWiseStatus(String userId, String status);

    List getKeywordWiseArtMasterList(String keyword);

    List searchKeywordCountMaster(String keyword);

    List<ContributorArtMarkupMaster> getArtIdWiseContributorArtMarkup(String artId);

    List<ContributorArtMarkupMaster> getArtIdAndShapeWiseContributorArtMarkup(String artId, String shape);

    List<ArtMaster> getAllArtMasterByStatus(String status);

    Boolean updateDArtMaster(ArtMasterUpdateReqDto artMasterUpdateReqDto);

    Boolean updateDsArtMaster();

    List gerReleaseStatusByArtMasterId(String artId);

    ContributorArtMarkupMaster getContrubuterMarkupByArtId(String artId);

    List getAllArtsAndProductByUserId(String userId);

    List getAllArtsAndProductBySort(SortRequest sortRequest);

    List<ArtMaster> getAllArtMasterByStatusandUserId(String status, String userId);

    Integer getCountOfArtByStatus(String status, String userId);

    List<ArtMaster> getArtByKeyword(String keyword, String userId);

    Set<String> getAllKeywordByArtName(String artName);

    void getAllArtsAndProductBySort1(SortRequest sortRequest);

    int getAllArtsByUserId(String userId);

   List ArtFilterNew(ArtMasterFilterReqDto artMasterFilterReqDto, String type, String text, Integer page, Integer number);


//    List<ArtMaster> getRecentlyViewedArts(int limit);
}
