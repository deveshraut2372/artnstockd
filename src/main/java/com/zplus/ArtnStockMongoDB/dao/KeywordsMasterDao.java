package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.KeywordsMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/KeywordsMaster")
@CrossOrigin(origins = "*")
public interface    KeywordsMasterDao extends MongoRepository<KeywordsMaster,String> {


}
