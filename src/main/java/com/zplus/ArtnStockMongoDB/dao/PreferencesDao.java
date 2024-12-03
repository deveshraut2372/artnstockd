package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.Preferences;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesDao extends MongoRepository<Preferences,Integer> {
}
