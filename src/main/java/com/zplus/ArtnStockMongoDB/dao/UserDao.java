package com.zplus.ArtnStockMongoDB.dao;

import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.model.UserServeMaster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends MongoRepository<UserMaster,String> {

    @Query("{userId :?0}")
    UserMaster getId(String userId);

    UserMaster findByEmailAddress(String userName);

    UserMaster findAllByEmailAddress(String emailAddress);

    @Query("{userId :?0}")
    UserMaster getUserMaster(@Param("userId") String userId);

    Optional<UserMaster> findByUserId(String userId);

    @Query("{displayName :?0}")
    UserMaster getDisplayNameWiseUser(String displayName);


    @Query("{userFirstName :?0}")
    UserMaster getuser(String userFirstName);

    boolean existsByDisplayName(String displayName);


    List<UserServeMaster> findAllByUserIdAndLogin(String userId, boolean b);

//    List findAllByLogin(boolean b);

    @Query("{'login':?0}")
    List getcountsByLogin(boolean b);

    boolean existsByEmailAddress(String emailAddress);

//    @Query(value = "{ 'userFirstName': { $regex: ?0, $options: 'i' }, 'status': 'Active' }", fields = "{ 'userId': 1 }")

    @Query(value = "{ 'status':?0}" )
    List<UserMaster> getActiveUserIds(String active);



//    Optional<UserMaster> findByUserMobNo(String username);
}
