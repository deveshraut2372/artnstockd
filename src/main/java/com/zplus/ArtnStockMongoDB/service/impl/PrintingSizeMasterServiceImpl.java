package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.PrintingSizeMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.PrintingSizeRequest;
import com.zplus.ArtnStockMongoDB.model.PrintingMaterialMaster;
import com.zplus.ArtnStockMongoDB.model.PrintingSizeMaster;
import com.zplus.ArtnStockMongoDB.service.PrintingSizeMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrintingSizeMasterServiceImpl implements PrintingSizeMasterService {

    @Autowired
    private PrintingSizeMasterDao printingSizeMasterDao;

    @Override
    public Boolean createPrintingSizeMaster(PrintingSizeRequest printingSizeRequest) {
        PrintingSizeMaster printingSizeMaster = new PrintingSizeMaster();
        BeanUtils.copyProperties(printingSizeRequest, printingSizeMaster);
        try {
            printingSizeMasterDao.save(printingSizeMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updatePrintingSizeMaster(PrintingSizeRequest printingSizeRequest) {
        PrintingSizeMaster printingSizeMaster = new PrintingSizeMaster();
        printingSizeMaster.setPrintingSizeId(printingSizeRequest.getPrintingSizeId());
        BeanUtils.copyProperties(printingSizeRequest, printingSizeMaster);
        try {
            printingSizeMasterDao.save(printingSizeMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllPrintingSizeMaster() {
        List<PrintingSizeMaster> printingSizeMasterList = printingSizeMasterDao.findAll();
        return printingSizeMasterList;
    }

    @Override
    public PrintingSizeMaster editPrintingMaterialMaster(String printingSizeId) {
        PrintingSizeMaster printingSizeMaster = new PrintingSizeMaster();
        try {
            Optional<PrintingSizeMaster> optionalPrintingSizeMaster = printingSizeMasterDao.findById(printingSizeId);
            optionalPrintingSizeMaster.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, printingSizeMaster));
            return printingSizeMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return printingSizeMaster;
        }
    }
    @Override
    public List getActivePrintingSizeMasterList() {
        List<PrintingSizeMaster> printingSizeMasterList = printingSizeMasterDao.findByPrintingSizeStatus("Active");
        return printingSizeMasterList;
    }
}
