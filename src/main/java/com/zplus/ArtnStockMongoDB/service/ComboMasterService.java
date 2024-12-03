package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ComboReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateComboReqDto;
import com.zplus.ArtnStockMongoDB.model.ComboMaster;

import java.util.List;

public interface ComboMasterService {
    Boolean createComboMaster(ComboReqDto comboReqDto);

    Boolean updateComboMaster(UpdateComboReqDto updateComboReqDto);

    List getAllComboMaster();

    ComboMaster editComboMaster(String comboId);

    List getActiveComboMaster();

    List getArtProductIdWiseComboMasterList(String artProductId);

    List getAdminArtProductIdWiseComboMasterList(String adminArtProductId);
}
