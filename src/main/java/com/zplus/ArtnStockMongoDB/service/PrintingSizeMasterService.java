package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.PrintingSizeRequest;
import com.zplus.ArtnStockMongoDB.model.PrintingSizeMaster;

import java.util.List;

public interface PrintingSizeMasterService {
    Boolean createPrintingSizeMaster(PrintingSizeRequest printingSizeRequest);

    Boolean updatePrintingSizeMaster(PrintingSizeRequest printingSizeRequest);

    List getAllPrintingSizeMaster();

    PrintingSizeMaster editPrintingMaterialMaster(String printingSizeId);

    List getActivePrintingSizeMasterList();
}
