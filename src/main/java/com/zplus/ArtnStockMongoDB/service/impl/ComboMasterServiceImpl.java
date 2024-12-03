package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.configuration.RandomNumberGenerator;
import com.zplus.ArtnStockMongoDB.configuration.UniqueNumber;
import com.zplus.ArtnStockMongoDB.dao.AdminArtProductMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ArtProductMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ComboDao;
import com.zplus.ArtnStockMongoDB.dto.req.ComboReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.UpdateComboReqDto;
import com.zplus.ArtnStockMongoDB.model.AdminArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.ArtProductMaster;
import com.zplus.ArtnStockMongoDB.model.ComboMaster;
import com.zplus.ArtnStockMongoDB.model.SizeAndPrice;
import com.zplus.ArtnStockMongoDB.service.ComboMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.SpringCacheAnnotationParser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ComboMasterServiceImpl implements ComboMasterService {

    @Autowired
    private ComboDao comboDao;

    @Autowired
    private ArtProductMasterDao artProductMasterDao;

    @Autowired
    private AdminArtProductMasterDao adminArtProductMasterDao;


//    @Override
//    public Boolean createComboMaster(ComboReqDto comboReqDto) {
//        Boolean flag=false;
//        ComboMaster comboMaster1=new ComboMaster();
//        List<ArtProductMaster> artProductMasterList = new ArrayList<>();
//        ComboMaster comboMaster = new ComboMaster();
//        BeanUtils.copyProperties(comboReqDto, comboMaster);
//
//        for (String id :comboReqDto.getArtProductId()) {
//            ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(id);
//            artProductMaster.setArtProductId(id);
//            artProductMasterList.add(artProductMaster);
//        }
//        comboMaster.setArtProductMasterList(artProductMasterList);
//        comboMaster.setCount(comboReqDto.getArtProductId().size());
//        try {
//          comboMaster1=comboDao.save(comboMaster);
//            flag = true;
//        }  catch (Exception e) {
//            e.printStackTrace();
//            flag= false;
//        }
//        String comboNo = UniqueNumber.uniqueNumber();
//        System.out.println("comboNo" + comboNo);
//        try {
//            Optional<ComboMaster> optionalComboMaster = comboDao.findByComboId(comboMaster1.getComboId());
//            if (optionalComboMaster.isPresent()) {
//                ComboMaster master = optionalComboMaster.get();
//                master.setComboNo(comboNo);
//                ComboMaster comboMaster2=comboDao.save(master);
//                System.out.println("comboNo111..." + comboMaster2.getComboNo());
//
//                flag = true;
//            } else {
//                flag = false;
//            }
//            return flag;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }


//    @Override
//    public Boolean createComboMaster(ComboReqDto comboReqDto) {
//        Boolean flag = false;
//        ComboMaster comboMaster1 = new ComboMaster();
//        List<ArtProductMaster> artProductMasterList = new ArrayList<>();
//        ComboMaster comboMaster = new ComboMaster();
//        BeanUtils.copyProperties(comboReqDto, comboMaster);
//
//        for (String id : comboReqDto.getArtProductId()) {
//            ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(id);
//            artProductMaster.setArtProductId(id);
//            artProductMasterList.add(artProductMaster);
//        }
//        comboMaster.setArtProductMaster(artProductMasterList);
//        comboMaster.setCount(comboReqDto.getArtProductId().size());
//        try {
//            comboMaster1 = comboDao.save(comboMaster);
//            flag = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            flag = false;
//        }
//        String comboNo = UniqueNumber.generateUniqueNumber();
//        System.out.println("comboNo: " + comboNo);
//        try {
//            Optional<ComboMaster> optionalComboMaster = comboDao.findByComboId(comboMaster1.getComboId());
//            if (optionalComboMaster.isPresent()) {
//                ComboMaster master = optionalComboMaster.get();
//                master.setComboNo(comboNo);
//                ComboMaster comboMaster2 = comboDao.save(master);
//                System.out.println("comboNo111: " + comboMaster2.getComboNo());
//                flag = true;
//            } else {
//                flag = false;
//            }
//            return flag;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//

    @Override
    public Boolean createComboMaster(ComboReqDto comboReqDto) {
        Boolean flag = false;
        ComboMaster comboMaster1 = new ComboMaster();

        // new changes
        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();

//        List<ArtProductMaster> artProductMasterList = new ArrayList<>();
        ComboMaster comboMaster = new ComboMaster();
        BeanUtils.copyProperties(comboReqDto, comboMaster);

         Double price=0.0;

        for (String id : comboReqDto.getAdminArtProductId()) {
            AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(id);
            adminArtProductMaster.setAdminArtProductId(id);
            adminArtProductMasterList.add(adminArtProductMaster);

            double artprice=0.0;
            artprice=adminArtProductMaster.getArtMaster().getFinalArtPrice();

            SizeAndPrice sizeAndPrice=adminArtProductMaster.getSizeAndPrices().stream().filter(sizeAndPrice1 -> sizeAndPrice1.getSize().equalsIgnoreCase(comboReqDto.getSize())).findFirst().get();
            artprice=artprice+sizeAndPrice.getSellPrice();
            price=price+artprice;
        }

        comboMaster.setPrice(price);
        comboMaster.setAdminArtProductMaster(adminArtProductMasterList);
        comboMaster.setCount(comboReqDto.getAdminArtProductId().size());
        try {
            comboMaster1 = comboDao.save(comboMaster);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        String comboNo = UniqueNumber.generateUniqueNumber();
        System.out.println("comboNo: " + comboNo);
        try {
            Optional<ComboMaster> optionalComboMaster = comboDao.findByComboId(comboMaster1.getComboId());
            if (optionalComboMaster.isPresent()) {
                ComboMaster master = optionalComboMaster.get();
                master.setComboNo(comboNo);
                ComboMaster comboMaster2 = comboDao.save(master);
                System.out.println("comboNo111: " + comboMaster2.getComboNo());
                flag = true;
            } else {
                flag = false;
            }
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


//    @Override
//    public Boolean updateComboMaster(UpdateComboReqDto updateComboReqDto) {
//        ComboMaster comboMaster = new ComboMaster();
//
//        comboMaster.setComboId(updateComboReqDto.getComboId());
//        BeanUtils.copyProperties(updateComboReqDto, comboMaster);
//        for (String id : updateComboReqDto.getArtProductId()) {
//            ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(id);
//            List<ArtProductMaster> artProductMasterList = new ArrayList<>();
//            artProductMaster.setArtProductId(id);
//            artProductMasterList.add(artProductMaster);
//            comboMaster.setArtProductMaster(artProductMasterList);
//        }
//        try {
//            comboDao.save(comboMaster);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }


    @Override
    public Boolean updateComboMaster(UpdateComboReqDto updateComboReqDto) {
        ComboMaster comboMaster = new ComboMaster();
        comboMaster.setComboId(updateComboReqDto.getComboId());
        BeanUtils.copyProperties(updateComboReqDto, comboMaster);

        List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
        for (String id : updateComboReqDto.getAdminArtProductId()) {
            ArtProductMaster artProductMaster = artProductMasterDao.getArtProductMaster(id);
          AdminArtProductMaster adminArtProductMaster=adminArtProductMasterDao.getAdminArtProductMaster(id);
            adminArtProductMaster.setAdminArtProductId(id);
            adminArtProductMasterList.add(adminArtProductMaster);
        }
        comboMaster.setAdminArtProductMaster(adminArtProductMasterList);

        try {
            comboDao.save(comboMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllComboMaster() {
        List<ComboMaster> list=comboDao.findAll();
        System.out.println("aaa"+list);
        return list;

    }

    @Override
    public ComboMaster editComboMaster(String comboId) {
        ComboMaster comboMaster=new ComboMaster();
        try {
            Optional<ComboMaster> comboMaster1=comboDao.findById(comboId);
            comboMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, comboMaster));
            return comboMaster;
        }
        catch (Exception e) {
            e.printStackTrace();
            return comboMaster;
        }
    }

    @Override
    public List getActiveComboMaster() {
        return comboDao.findAllByStatus("Active");
    }

    @Override
    public List getArtProductIdWiseComboMasterList(String artProductId) {
        List list=comboDao.findByArtProductMasterArtProductId(artProductId);
        return list;
    }

    @Override
    public List getAdminArtProductIdWiseComboMasterList(String adminArtProductId) {
        List list=comboDao.findByAdminArtProductMaster_AdminArtProductId(adminArtProductId);
        return list;
    }

    public Boolean createComboMasterd(ComboReqDto comboReqDto) {

        try
        {
            ComboMaster comboMaster=new ComboMaster();
            String comboNo="Combo"+System.currentTimeMillis();
            do {
                 comboNo="Combon"+System.currentTimeMillis();
            }while(comboDao.existsByComboNo(comboNo));

            //            comboMaster.setComboNo(comboNo);

            comboMaster.setCount(comboReqDto.getAdminArtProductId().size()+comboReqDto.getArtProductId().size());

//            comboMaster.setPrice();
            comboMaster.setImage(comboReqDto.getImage());
            comboMaster.setStock(comboReqDto.getStock());
            comboMaster.setStockQuantity(comboReqDto.getStockQuantity());
            comboMaster.setTitle(comboReqDto.getTitle());
            comboMaster.setDescription(comboReqDto.getDescription());
            comboMaster.setStatus("Active");
            comboMaster.setSize(comboReqDto.getSize());


            Double apprice=0.0;
            List<ArtProductMaster> artProductMasterList=new ArrayList<>();

            for(String id:comboReqDto.getArtProductId())
            {
                System.out.println( "  ArtProductId ="+id);
                ArtProductMaster artProductMaster=new ArtProductMaster();
                artProductMaster=artProductMasterDao.findById(id).get();
                artProductMasterList.add(artProductMaster);

                SizeAndPrice sizeAndPrice1=artProductMaster.getSizeAndPrices().stream().filter(sizeAndPrice -> sizeAndPrice.getSize().equalsIgnoreCase(comboReqDto.getSize())).findFirst().get();
                apprice=apprice+sizeAndPrice1.getSellPrice();
                apprice=apprice+artProductMaster.getArtMaster().getPrice();
            }

            List<AdminArtProductMaster> adminArtProductMasterList=new ArrayList<>();
            for(String id:comboReqDto.getAdminArtProductId())
            {
                System.out.println( "  ArtProductId ="+id);
                AdminArtProductMaster adminArtProductMaster=new AdminArtProductMaster();
                adminArtProductMaster=adminArtProductMasterDao.findById(id).get();
                adminArtProductMasterList.add(adminArtProductMaster);

                SizeAndPrice sizeAndPrice1=adminArtProductMaster.getSizeAndPrices().stream().filter(sizeAndPrice -> sizeAndPrice.getSize().equalsIgnoreCase(comboReqDto.getSize())).findFirst().get();
                apprice=apprice+sizeAndPrice1.getSellPrice();
                apprice=apprice+adminArtProductMaster.getArtMaster().getPrice();
            }
            comboMaster.setPrice(apprice);

            comboMaster.setArtProductMaster(artProductMasterList);
            comboMaster.setAdminArtProductMaster(adminArtProductMasterList);

            comboDao.save(comboMaster);

            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }


}
