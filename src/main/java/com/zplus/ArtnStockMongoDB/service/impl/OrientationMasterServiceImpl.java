package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.OrientationMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.ContributerMarkupRes;
import com.zplus.ArtnStockMongoDB.dto.req.OrientationMasterRequest;
import com.zplus.ArtnStockMongoDB.model.OrientationMaster;
import com.zplus.ArtnStockMongoDB.service.OrientationMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrientationMasterServiceImpl implements OrientationMasterService {

    @Autowired
    private OrientationMasterDao orientationMasterDao;

    @Override
    public Boolean createOrientationMaster(OrientationMasterRequest orientationMasterRequest) {
        OrientationMaster orientationMaster = new OrientationMaster();
        System.out.println("price.."+orientationMasterRequest.getPrice());
        System.out.println("per......" +orientationMasterRequest.getContributorMarkupPercentage());
        double contPrice=orientationMasterRequest.getPrice()*orientationMasterRequest.getContributorMarkupPercentage()/100;
        System.out.println("contPrice"+contPrice);
        double marginAmount=contPrice*orientationMasterRequest.getArtMarginPercentage()/100;
        System.out.println("marginAmount " +marginAmount);
        double subTotalExpenses=orientationMasterRequest.getArtExpensesOne()+orientationMasterRequest.getArtExpensesTwo()+orientationMasterRequest.getArtExpensesThree();
        System.out.println("subTotalExpenses "+subTotalExpenses);
        double marginPer=subTotalExpenses*orientationMasterRequest.getMarginPercentage()/100;
        System.out.println("marginPer"+marginPer);
        double totalExpenses=subTotalExpenses+marginPer;
        System.out.println("totalExpenses "+totalExpenses);
        double basePrice=marginAmount+totalExpenses;
        System.out.println("basePrice"+basePrice);
        double sellPrice=contPrice+marginAmount+totalExpenses;
        System.out.println("sellPrice "+sellPrice);

        orientationMaster.setPrice(orientationMasterRequest.getPrice());
        orientationMaster.setContributorCalculatedPrice(contPrice);
        orientationMaster.setMarginAmount(marginAmount);
        orientationMaster.setSubTotalExpenses(subTotalExpenses);
        orientationMaster.setTotalExpenses(totalExpenses);
        orientationMaster.setBasePrice(basePrice);
        orientationMaster.setSellPrice(sellPrice);
        orientationMaster.setHeight(orientationMasterRequest.getHeight());
        orientationMaster.setWidth(orientationMasterRequest.getWidth());
        orientationMaster.setShapeStatus("Active");
        orientationMaster.setShape(orientationMasterRequest.getShape());
        BeanUtils.copyProperties(orientationMasterRequest,orientationMaster);
        BeanUtils.copyProperties(orientationMasterRequest, orientationMaster);
        try {
            orientationMasterDao.save(orientationMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateOrientationMaster(OrientationMasterRequest orientationMasterRequest) {
        OrientationMaster orientationMaster = new OrientationMaster();
        orientationMaster.setOrientationId(orientationMasterRequest.getOrientationId());
        BeanUtils.copyProperties(orientationMasterRequest, orientationMaster);
        try {
            orientationMasterDao.save(orientationMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllOrientationMaster() {
        List<OrientationMaster> orientationMasterList = orientationMasterDao.findAll();
        return orientationMasterList;
    }

    @Override
    public OrientationMaster editOrientationMaster(String orientationId) {
        OrientationMaster orientationMaster = new OrientationMaster();
        try {
            Optional<OrientationMaster> optionalShapeMaster = orientationMasterDao.findById(orientationId);
            optionalShapeMaster.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, orientationMaster));
            return orientationMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return orientationMaster;
        }
    }

    @Override
    public List getActiveOrientationMasterList() {
        List<OrientationMaster> orientationMasterList = orientationMasterDao.findByShapeStatus("Active");
        return orientationMasterList;
    }

    @Override
    public List getShapeWiseList(String shape) {
        List<OrientationMaster> orientationMasterList = orientationMasterDao.findByShape(shape);
        return orientationMasterList;
    }

    @Override
    public ContributerMarkupRes getContributerEarning100percentage(Double markup) {

        OrientationMaster orientationMaster =new OrientationMaster();
        List<OrientationMaster> orientationMasterList=orientationMasterDao.findAll();
        orientationMaster=orientationMasterList.get(0);

        ContributerMarkupRes contributerMarkupRes=new ContributerMarkupRes();
        if(markup==0)
        {
            contributerMarkupRes.setMarkup(markup);
            contributerMarkupRes.setSellingPrince(0.0);
            contributerMarkupRes.setBasicPrice(0.0);
            return contributerMarkupRes;
        }

        Double basicPrice=0.0;
        Double sellingPrice=0.0;

        Double marginAmount=0.0,expenses1=0.0,expenses2=0.0,expenses3=0.0,addMarginExpenses=0.0;
        Double subtotalmargin=0.0;
        Double totalExpenses=0.0;
        marginAmount=markup*25/100;
        expenses1=markup*12.5/100;
        expenses2=markup*7.5/100;
        expenses3=200.0;
        subtotalmargin=expenses1+expenses2+expenses3;
        addMarginExpenses=subtotalmargin*25/100;
        System.out.println("  exp 1 ="+expenses1+"  exp 2 ="+expenses2+" exp 3 ="+expenses3);
        totalExpenses=subtotalmargin+addMarginExpenses;
        basicPrice=marginAmount+totalExpenses;

        System.out.println("basic price"+basicPrice);
        System.out.println("total"+totalExpenses);

        sellingPrice=basicPrice+markup;

        contributerMarkupRes.setMarkup(markup);
        contributerMarkupRes.setSellingPrince(sellingPrice);
        contributerMarkupRes.setBasicPrice(basicPrice);

        return contributerMarkupRes;
    }
}
