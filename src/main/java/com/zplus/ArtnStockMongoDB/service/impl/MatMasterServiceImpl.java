package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.MatDao;
import com.zplus.ArtnStockMongoDB.dto.req.MatReqDto;
import com.zplus.ArtnStockMongoDB.model.LimitedEditionMaster;
import com.zplus.ArtnStockMongoDB.model.MatMaster;
import com.zplus.ArtnStockMongoDB.service.MatMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatMasterServiceImpl implements MatMasterService {

    @Autowired
    private MatDao matDao;

    @Override
    public Boolean createMatMaster(MatReqDto matReqDto) {
            MatMaster matMaster = matDao.findByMatType(matReqDto.getMatType());
            if (matMaster != null) {
                matMaster.setFrameColor(matReqDto.getColor());
                matMaster.setMatType(matReqDto.getMatType());
                matMaster.setFrameColor(matReqDto.getColor());
                matMaster.setMatWidth(matReqDto.getMatWidth());
                matMaster.setStatus(matReqDto.getStatus());
                matMaster.setPrice(matReqDto.getPrice());
                try {
                    MatMaster m=matDao.save(matMaster);
                    System.out.println("m = " + m.getMatType());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                matMaster = new MatMaster();
                matMaster.setMatType(matReqDto.getMatType());
                matMaster.setFrameColor(matReqDto.getColor());
                matMaster.setMatWidth(matReqDto.getMatWidth());
                matMaster.setStatus(matReqDto.getStatus());
                matMaster.setPrice(matReqDto.getPrice());
                try {
                    MatMaster m1=matDao.save(matMaster);
                    System.out.println("m1 = " + m1.getMatType());
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

    @Override
    public Boolean updateMatMaster(MatReqDto matReqDto) {
        MatMaster matMaster = new MatMaster();
        matMaster.setMatId(matReqDto.getMatId());
        BeanUtils.copyProperties(matReqDto, matMaster);
        try {
            matDao.save(matMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllMatMaster() {
        return matDao.findAll();
    }

    @Override
    public MatMaster editMatMaster(String matId) {
        MatMaster matMaster = new MatMaster();
        try {
            Optional<MatMaster> matMasterOptional = matDao.findById(matId);
            matMasterOptional.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, matMaster));
            return matMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return matMaster;
        }
    }

    @Override
    public List getActiveMatMaster() {
        return matDao.findByStatus("Active");
    }

    @Override
    public MatMaster getTypeWiseList(String matType) {
        MatMaster matMaster = matDao.findByMatType(matType);
        return matMaster;
    }
}
