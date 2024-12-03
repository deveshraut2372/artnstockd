package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ArtMasterDao;
import com.zplus.ArtnStockMongoDB.dao.StyleMasterDao;
import com.zplus.ArtnStockMongoDB.dao.SubjectDao;
import com.zplus.ArtnStockMongoDB.dto.req.SubjectReqDto;
import com.zplus.ArtnStockMongoDB.model.StyleMaster;
import com.zplus.ArtnStockMongoDB.model.SubjectMaster;
import com.zplus.ArtnStockMongoDB.service.SubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private ArtMasterDao artMasterDao;

    @Autowired
    private StyleMasterDao styleMasterDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean createSubjectMaster(SubjectReqDto subjectReqDto) {
        Boolean flag = false;
        SubjectMaster subjectMaster = new SubjectMaster();
        List<SubjectMaster> subjectMasterList = subjectDao.findAllByType(subjectReqDto.getType());
        if (subjectMasterList != null) {
            if (subjectMasterList.size() < 10) {
                BeanUtils.copyProperties(subjectReqDto, subjectMaster);
                subjectDao.save(subjectMaster);
                flag=true;
            } else {
                BeanUtils.copyProperties(subjectReqDto, subjectMaster);
                subjectMaster.setType("none");
                subjectDao.save(subjectMaster);
                flag=true;
            }

        }
        return flag;
    }

    @Override
    public Boolean updateSubjectMaster(SubjectReqDto subjectReqDto) {
        SubjectMaster subjectMaster = new SubjectMaster();

        BeanUtils.copyProperties(subjectReqDto, subjectMaster);
        try {
            subjectDao.save(subjectMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllSubject() {
        return subjectDao.findAll();

    }

    @Override
    public SubjectMaster editSubjectMaster(String subjectId) {
        SubjectMaster subjectMaster = new SubjectMaster();
        try {
            Optional<SubjectMaster> subjectMaster1 = subjectDao.findById(subjectId);
            subjectMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, subjectMaster));
            return subjectMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return subjectMaster;
        }
    }

    @Override
    public List getActiveSubject() {
        List<SubjectMaster> subjectMasters = subjectDao.findAllBySubjectStatus("Active");
        return subjectMasters;

    }

    @Override
    public List<SubjectMaster> getTypeWiseSubjectList(String type) {

        List<SubjectMaster> subjectMasterList = subjectDao.findAllByType(type).stream().limit(10).collect(Collectors.toList());
        System.out.println("sssssssssssf...."+subjectMasterList.size());

        return subjectMasterList;


//        List alllist = new ArrayList();
//        Random rand = new Random();
//        ArtMaster artMaster=new ArtMaster();
//        ArtSubjectResDto artSubjectResDto = new ArtSubjectResDto();
//        int i = 0;
//        List<SubjectMaster> list = subjectDao.findAllBySubjectStatusAndType("Active",type);
//        System.out.println("lissst"+list.toString());
//
//        for (SubjectMaster subjectMaster : list) {
//            System.out.println("subjectList ......."+subjectMaster);
//            System.out.println("subjectId"+subjectMaster.getSubjectId());
//
//            List<ArtMaster> artMasterList=artMasterDao.findBySubjectMasterSubjectIdAndStatus(subjectMaster.getSubjectId(),"Active").stream().collect(Collectors.toList());
//            System.out.println("artmasterList.."+artMasterList);

        //            List<ArtSubjectResDto> artSubjectResDtoList = artMasterDao.findByStyleMaster_SubjectMaster_SubjectId(subjectMaster.getSubjectId());
//
//            System.out.println("artList......"+artSubjectResDtoList);
//
//            if (!artSubjectResDtoList.isEmpty()) {
//
//                ArtSubjectResDto randomElement = artSubjectResDtoList.get(rand.nextInt(artSubjectResDtoList.size()));
//                artSubjectResDto.setArtId(randomElement.getArtId());
//                artSubjectResDto.setArtImage(randomElement.getImage());
//                artSubjectResDto.setStatus(randomElement.getStatus());
//
//
//                artSubjectResDto.setSubjectId(subjectMaster.getSubjectId());
//                artSubjectResDto.setSubjectStatus(subjectMaster.getSubjectStatus());
//                artSubjectResDto.setImage(subjectMaster.getSubjectImage());
//                artSubjectResDto.setGridHeight(subjectMaster.getGridHeight());
//                artSubjectResDto.setType(subjectMaster.getType());
//                artSubjectResDto.setGridWidth(subjectMaster.getGridWidth());
//                artSubjectResDto.setGridColumn(subjectMaster.getGridColumn());
//            }
//            alllist.add(artSubjectResDto);
//
//            artSubjectResDto = new ArtSubjectResDto();
    }

    @Override
    public List<SubjectMaster> searchByText(String searchText) {
        List<SubjectMaster> subjectMasterList = subjectDao.findAllBySubjectNameLikeSearchText(searchText);
        return subjectMasterList;
    }

    @Override
    public List getArtDropdownTrue() {
        List<SubjectMaster> subjectMasterList = subjectDao.findByArtDropdown(true);
        return subjectMasterList;
    }

    @Override
    public Boolean deleteBySubjectId(String subjectId) {
        try {
            subjectDao.deleteById(subjectId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
//        return alllist;

}

//    @Override
//    public List getSubjectIdAndArtIdWiseList(String subjectId) {
//        List list = new ArrayList();
//        List<StyleMaster> styleMasterList = styleMasterDao.findBySubjectMasterSubjectId(subjectId);
//        System.out.println("styleList"+styleMasterList);
//        for (StyleMaster styleMaster : styleMasterList) {
//            List<ArtMaster> artMasterList = artMasterDao.findByStyleMasterStyleId(styleMaster.getStyleId());
//            System.out.println("artMaster"+artMasterList);
//            list.add(artMasterList);
//        }
//        return list;
//
//    }




