package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.PrintingMaterialServiceReqDto;
import com.zplus.ArtnStockMongoDB.model.PrintingMaterialMaster;

import java.util.List;

public interface PrintingMaterialService {
    Boolean createPrintingMaterialMaster(PrintingMaterialServiceReqDto printingMaterialServiceReqDto);

    Boolean updatePrintingMaterialMaster(PrintingMaterialServiceReqDto printingMaterialServiceReqDto);

    List getAllPrintingMaterialMaster();

    PrintingMaterialMaster editPrintingMaterialMaster(String printingMaterialId);

    List getActivePrintingMaterialMasterList();
}
