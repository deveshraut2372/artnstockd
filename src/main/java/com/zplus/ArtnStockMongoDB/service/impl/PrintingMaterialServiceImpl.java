package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.PrintingMaterialMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.PrintingMaterialServiceReqDto;
import com.zplus.ArtnStockMongoDB.model.MatMaster;
import com.zplus.ArtnStockMongoDB.model.PrintingMaterialMaster;
import com.zplus.ArtnStockMongoDB.service.PrintingMaterialService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrintingMaterialServiceImpl implements PrintingMaterialService {

    @Autowired
    private PrintingMaterialMasterDao printingMaterialMasterDao;

    @Override
    public Boolean createPrintingMaterialMaster(PrintingMaterialServiceReqDto printingMaterialServiceReqDto) {
        PrintingMaterialMaster printingMaterialMaster = new PrintingMaterialMaster();
        BeanUtils.copyProperties(printingMaterialServiceReqDto, printingMaterialMaster);
        try {
            printingMaterialMasterDao.save(printingMaterialMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updatePrintingMaterialMaster(PrintingMaterialServiceReqDto printingMaterialServiceReqDto) {
        PrintingMaterialMaster printingMaterialMaster = new PrintingMaterialMaster();
printingMaterialMaster.setPrintingMaterialId(printingMaterialServiceReqDto.getPrintingMaterialId());
        BeanUtils.copyProperties(printingMaterialServiceReqDto, printingMaterialMaster);
        try {
            printingMaterialMasterDao.save(printingMaterialMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllPrintingMaterialMaster() {
    List<PrintingMaterialMaster> printingMaterialMasterList = printingMaterialMasterDao.findAll();
        return printingMaterialMasterList;
    }

    @Override
    public PrintingMaterialMaster editPrintingMaterialMaster(String printingMaterialId) {
        PrintingMaterialMaster printingMaterialMaster = new PrintingMaterialMaster();
        try {
            Optional<PrintingMaterialMaster> optionalPrintingMaterialMaster = printingMaterialMasterDao.findById(printingMaterialId);
            optionalPrintingMaterialMaster.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, printingMaterialMaster));
            return printingMaterialMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return printingMaterialMaster;
        }
    }

    @Override
    public List getActivePrintingMaterialMasterList() {
    List<PrintingMaterialMaster> printingMaterialMasterList = printingMaterialMasterDao.findByPrintingMaterialStatus("Active");
        return printingMaterialMasterList;
    }
}
