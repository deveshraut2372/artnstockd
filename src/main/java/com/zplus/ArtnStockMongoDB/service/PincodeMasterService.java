package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.PincodeMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.dto.res.PincodeResDto;
import com.zplus.ArtnStockMongoDB.model.PincodeMaster;

import java.util.List;

public interface PincodeMasterService {
    Boolean createPincodeMaster(PincodeMasterRequest pincodeMasterRequest);

    Boolean updatePincodeMaster(PincodeMasterRequest pincodeMasterRequest);

    List getAllPincodeMaster();

    PincodeMaster editPincodeMaster(String pinCodeId);

    List getActivePincodeMaster();

    Message getPinCode(Integer pinCode);

    List<PincodeResDto> getPincodesActive();

    Boolean deleteByPinCodeId(String pinCodeId);
}
