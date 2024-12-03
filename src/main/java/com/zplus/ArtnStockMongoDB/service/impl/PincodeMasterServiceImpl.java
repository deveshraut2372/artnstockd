package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.PincodeMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.PincodeMasterRequest;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.dto.res.PincodeResDto;
import com.zplus.ArtnStockMongoDB.model.CountryMaster;
import com.zplus.ArtnStockMongoDB.model.PincodeMaster;
import com.zplus.ArtnStockMongoDB.model.ProductMaster;
import com.zplus.ArtnStockMongoDB.service.PincodeMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PincodeMasterServiceImpl implements PincodeMasterService {

    @Autowired
    private PincodeMasterDao pincodeMasterDao;

    @Override
    public Boolean createPincodeMaster(PincodeMasterRequest pincodeMasterRequest) {

        System.out.println(" pincodeMasterRequest == "+pincodeMasterRequest.toString());
        PincodeMaster pincodeMaster = new PincodeMaster();
        CountryMaster countryMaster=new CountryMaster();
        countryMaster.setCountryId(pincodeMasterRequest.getCountryId());
        pincodeMaster.setCountryMaster(countryMaster);
        BeanUtils.copyProperties(pincodeMasterRequest, pincodeMaster);
        pincodeMaster.setStatus("Active");
        try {
            pincodeMasterDao.save(pincodeMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updatePincodeMaster(PincodeMasterRequest pincodeMasterRequest) {
        PincodeMaster pincodeMaster = new PincodeMaster();
        CountryMaster countryMaster=new CountryMaster();
        countryMaster.setCountryId(pincodeMasterRequest.getCountryId());
        pincodeMaster.setCountryMaster(countryMaster);
        pincodeMaster.setPinCodeId(pincodeMasterRequest.getPinCodeId());
        BeanUtils.copyProperties(pincodeMasterRequest, pincodeMaster);
        pincodeMaster.setStatus(pincodeMasterRequest.getStatus());
        try {
            pincodeMasterDao.save(pincodeMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllPincodeMaster() {
        return pincodeMasterDao.findAll();
    }

    @Override
    public PincodeMaster editPincodeMaster(String pinCodeId) {
        PincodeMaster pincodeMaster=new PincodeMaster();
        try {
            Optional<PincodeMaster> pincodeMasterOptional=pincodeMasterDao.findById(pinCodeId);
            pincodeMasterOptional.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, pincodeMaster));
            return pincodeMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return pincodeMaster;
        }
    }

    @Override
    public List getActivePincodeMaster() {
        return pincodeMasterDao.findByStatus("Active");
    }
    @Override
    public Message getPinCode(Integer pinCode) {
        Message message = new Message();
        PincodeMaster pincodeMaster = pincodeMasterDao.findByPinCode(pinCode);

        if (pincodeMaster != null) {
            if (pincodeMaster.getPinCode() != null) {
                message.setMessage("Pincode is in the database");
                message.setFlag(true);
            } else {
                message.setMessage("Pincode not in database");
                message.setFlag(false);
            }
        } else {
            message.setMessage("Pincode not in database");
            message.setFlag(false);
        }
        return message;
    }

    @Override
    public List<PincodeResDto> getPincodesActive() {

        List<PincodeMaster> productMasters=new ArrayList<>();
        productMasters=pincodeMasterDao.findAllByStatus("Active");

                List<PincodeResDto> pincodeResDtoList = productMasters.stream()
                        .map(response -> pincodeResDtoList(response))
                        .collect(Collectors.toList());

        return pincodeResDtoList;
    }

    @Override
    public Boolean deleteByPinCodeId(String pinCodeId) {
        try {
            pincodeMasterDao.deleteById(pinCodeId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private PincodeResDto pincodeResDtoList(PincodeMaster response) {
        PincodeResDto pincodeResDto=new PincodeResDto();
        pincodeResDto.setPinCodeId(response.getPinCodeId());
        pincodeResDto.setPinCode(response.getPinCode());
        pincodeResDto.setStatus(response.getStatus());
        if(response.getCountryMaster()==null)
        {
            pincodeResDto.setCountryName(null);
            pincodeResDto.setCountryId(null);
        }else {
            pincodeResDto.setCountryName(response.getCountryMaster().getCountryName());
            pincodeResDto.setCountryId(response.getCountryMaster().getCountryId());
        }
        return pincodeResDto;
    }
}
