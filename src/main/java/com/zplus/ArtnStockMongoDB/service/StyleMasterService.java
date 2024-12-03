package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.StyleReqDto;
import com.zplus.ArtnStockMongoDB.model.StyleMaster;

import java.util.List;

public interface StyleMasterService {

    Boolean createStyleMaster(StyleReqDto styleReqDto);

    Boolean updateStyleMaster(StyleReqDto styleReqDto);

    List getAllStyleMaster();

    StyleMaster editStyleMaster(String styleId);

    List getActiveStyleMaster();

    List getArtDropdownTrue();

    Boolean deleteByStyleId(String styleId);
}
