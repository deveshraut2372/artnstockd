package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.AddDetailsGetReq;
import com.zplus.ArtnStockMongoDB.dto.req.AddDetailsMasterReq;
import com.zplus.ArtnStockMongoDB.dto.req.DeleteAddDetailsreq;
import com.zplus.ArtnStockMongoDB.model.AddDetailsMaster;

import java.util.List;

public interface AddDetailsMasterService {


    Boolean createArtDetails(AddDetailsMasterReq addDetailsMasterReq);

    Boolean updateArtDetails(AddDetailsMasterReq addDetailsMasterReq);

    AddDetailsMaster getArtDetails(AddDetailsGetReq addDetailsGetReq);

    Boolean checkExistOrNot(AddDetailsGetReq addDetailsGetReq);

    List<AddDetailsMaster> getAllAddDetails();

    Boolean deleteAddDetails(DeleteAddDetailsreq deleteAddDetailsreq);
}
