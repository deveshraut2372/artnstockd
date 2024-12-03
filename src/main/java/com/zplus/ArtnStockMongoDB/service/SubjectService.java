package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.SubjectReqDto;
import com.zplus.ArtnStockMongoDB.model.SubjectMaster;

import java.util.List;

public interface SubjectService {
    Boolean createSubjectMaster(SubjectReqDto subjectReqDto);

    Boolean updateSubjectMaster(SubjectReqDto subjectReqDto);

    List getAllSubject();

    SubjectMaster editSubjectMaster(String subjectId);

    List getActiveSubject();

    List getTypeWiseSubjectList(String type);

    List<SubjectMaster> searchByText(String searchText);

    List getArtDropdownTrue();

    Boolean deleteBySubjectId(String subjectId);

//    List getSubjectIdAndArtIdWiseList(String subjectId);

}
