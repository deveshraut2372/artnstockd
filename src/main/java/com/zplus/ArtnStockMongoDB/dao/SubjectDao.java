package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.SubjectMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SubjectDao extends MongoRepository<SubjectMaster,String> {

    List<SubjectMaster> findAllBySubjectStatus(String active);


//    List<SubjectMaster> findAllBySubjectStatusAndType(String active, String type);

    List<SubjectMaster> findAllByType(String type);

    @Query("{'subjectName' : { '$regex' : ?0 , $options: 'i'}}")
    List<SubjectMaster> findAllBySubjectNameLikeSearchText(String searchText);

    List<SubjectMaster> findByArtDropdown(boolean artDropdown);

    @Query("{subjectId :?0}")
    SubjectMaster getSubject(String subjectId);
}

