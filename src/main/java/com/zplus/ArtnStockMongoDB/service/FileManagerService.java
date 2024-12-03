package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.FileManagerMasterRes;
import com.zplus.ArtnStockMongoDB.dto.res.FileManagerRes;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.FileManagerMaster;

import java.util.List;

public interface FileManagerService {
    Boolean createFileManagerMaster(FileManagerReqDto fileManagerReqDto);

    Boolean updateFileManagerMaster(FileManagerReqDto fileManagerReqDto);

    List getAllFileManagerMaster();

    FileManagerMaster editFileManagerMaster(String fileManagerId);

    List getActiveFileManagerMaster();

    List<FileManagerMasterRes> getUserIdWiseFileManagerList(String userId);

    List<ArtMaster> getFileManagerIdWiseArtList(String fileManagerId);

    List<FileManagerRes> getUserIdWiseData(String userId);

    Boolean fileManagerIdWiseAddArt(AddArtRequestDto addArtRequestDto);

    Boolean updateIdWiseTitle(UpdateFileManagerTitleReqDto updateFileManagerTitleReqDto);

    Boolean deleteFileManager(String fileManagerId);

    List FileManagerFilter(FilterReqDto filterReqDto);

    Boolean fileManagerIdWiseAddAdminArtProduct(AddAdminArtProductReq addAdminArtProductReq);

    Boolean fileManagerIdWiseAddArtAndAdminProduct(AddArtAndAdminProductRequestDto addArtAndAdminProductRequestDto);

    Boolean fileManagerIdWiseAddArtAndAdminProduct1(AddArtAndAdminProductRequest addArtAndAdminProductRequest);

    List UserIdwiseArtAndProduct(String userId);

    List<FileManagerMasterRes> getFileManagerByUserIdAndSort(FileManagerSortRequest fileManagerSortRequest);

    Boolean DeletefileManagerIdWiseAddArtAndAdminProduct(AddArtAndAdminProductRequestDto addArtAndAdminProductRequestDto);

    Boolean CheckFilePresentOrNot(CheckFileReq checkFileReq);

    List<ArtMaster> getFileManagerIdWiseArtList1(String fileManagerId, String sortType);

    List getFileManagersListByUserIdAndSort(FileManagerListSortRequest fileManagerListSortRequest);

    int getCountByCategoryWise(FileManagerCountReq fileManagerCountReq);

    Boolean updateCoverImage(UpdateCoverImageReq updateCoverImageReq);

//    int getFileManagersCountByUserIdAndSort(FileManagerSortRequest fileManagerSortRequest);


//    List<ArtMaster> getList(String fileManagerId);
}
