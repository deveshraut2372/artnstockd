package com.zplus.ArtnStockMongoDB.service.impl;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.zplus.ArtnStockMongoDB.dao.ArtMasterDao;
import com.zplus.ArtnStockMongoDB.dao.CustomerReviewMasterDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.req.*;
import com.zplus.ArtnStockMongoDB.dto.res.CustomerReviewMasterRes;
import com.zplus.ArtnStockMongoDB.dto.res.Message;
import com.zplus.ArtnStockMongoDB.model.*;
import com.zplus.ArtnStockMongoDB.service.CustomerReviewMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class CustomerReviewMasterServiceImpl implements CustomerReviewMasterService {

    @Autowired
    private CustomerReviewMasterDao customerReviewMasterDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ArtMasterDao artMasterDao;

    @Override
    public Boolean createCustomerReviewMaster(CustomerReviewMasterReq customerReviewMasterReq) {


        //        if (customerReviewMasterReq.getArtProductId() != null) {
//            ArtProductMaster artProductMaster = new ArtProductMaster();
//            artProductMaster.setArtProductId(customerReviewMasterReq.getArtProductId());
//            customerReviewMaster.setArtProductMaster(artProductMaster);
//        }
//
//        if (customerReviewMasterReq.getArtFrameId() != null) {
//            ArtFrameMaster artFrameMaster = new ArtFrameMaster();
//            artFrameMaster.setArtFrameId(customerReviewMasterReq.getArtFrameId());
//            customerReviewMaster.setArtFrameMaster(artFrameMaster);
//        }

        CustomerReviewMaster customerReviewMaster = new CustomerReviewMaster();
        ArtMaster artMaster = artMasterDao.getArtId(customerReviewMasterReq.getArtId());

        if (artMaster == null) {
            throw new UserNotFoundException("Art not found for ID: " + customerReviewMasterReq.getArtId());
        } else {
            customerReviewMaster.setArtMaster(artMaster);
        }
        UserMaster userMaster = userDao.getUserMaster(customerReviewMasterReq.getUserId());
        if (userMaster == null) {
            throw new UserNotFoundException("User not found for ID: " + customerReviewMasterReq.getUserId());
        } else {
            customerReviewMaster.setUserMaster(userMaster);
        }
        customerReviewMaster.setDate(new Date());
        customerReviewMaster.setStatus("Active");
        customerReviewMaster.setLikeCount(0);
        BeanUtils.copyProperties(customerReviewMasterReq, customerReviewMaster);
        customerReviewMaster.setReviewImage(customerReviewMasterReq.getReviewImage());
        try {
            CustomerReviewMaster c = customerReviewMasterDao.save(customerReviewMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean updateCustomerReviewMaster(UpdateCustomerReviewMasterReq updateCustomerReviewMasterReq) {
        CustomerReviewMaster customerReviewMaster = new CustomerReviewMaster();

        ArtMaster artMaster = new ArtMaster();
        artMaster.setArtId(updateCustomerReviewMasterReq.getArtId());
        customerReviewMaster.setArtMaster(artMaster);

        UserMaster userMaster = new UserMaster();
        userMaster.setUserId(updateCustomerReviewMasterReq.getUserId());
        customerReviewMaster.setUserMaster(userMaster);

        customerReviewMaster.setCustomerReviewId(updateCustomerReviewMasterReq.getCustomerReviewId());
        BeanUtils.copyProperties(updateCustomerReviewMasterReq, customerReviewMaster);
        try {
            customerReviewMasterDao.save(customerReviewMaster);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List getAllCustomerReviewMaster() {
        List<CustomerReviewMaster> customerReviewMasterList = customerReviewMasterDao.findAll();
        return customerReviewMasterList;
    }

    @Override
    public CustomerReviewMaster editCustomerReviewMaster(String customerReviewId) {
        CustomerReviewMaster customerReviewMaster = new CustomerReviewMaster();
        try {
            Optional<CustomerReviewMaster> customerReviewMaster1 = customerReviewMasterDao.findById(customerReviewId);
            customerReviewMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, customerReviewMaster));
            return customerReviewMaster;
        } catch (Exception e) {
            e.printStackTrace();
            return customerReviewMaster;
        }
    }

    @Override
    public List getActiveCustomerReviewMaster() {
        List<CustomerReviewMaster> customerReviewMasterList = customerReviewMasterDao.findByStatus("Active");
        return customerReviewMasterList;
    }

    @Override
    public List getArtIdWiseCustomerReviewMaster(String artId) {
        System.out.println("art" + artId);
        List<CustomerReviewMaster> list = new ArrayList();

        List<CustomerReviewMasterRes> list1=new ArrayList<>();


        if (artId != null) {
            list = customerReviewMasterDao.findByArtMasterArtId(artId);
            for (CustomerReviewMaster customerReviewMaster : list) {
                CustomerReviewMasterRes customerReviewMasterRes = new CustomerReviewMasterRes();
                BeanUtils.copyProperties(customerReviewMaster, customerReviewMasterRes);
                LocalDateTime endDateTime = LocalDateTime.now();
                Instant instant = customerReviewMaster.getDate().toInstant();
                LocalDateTime statDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                Duration duration = Duration.between(statDateTime, endDateTime);
                System.out.println("  Duration =" + duration.toString());

//                if(customerReviewMaster.getDate().getMonth()==(new Date().getMonth())) {
                if (statDateTime.getDayOfMonth()==endDateTime.getDayOfMonth()) {
                    if (duration.toHours() == 0) {
                        if (duration.toMinutes() == 0) {
//                            customerReviewMasterRes.setReviewTime(duration.toMinutes() + " Min ago");
                            if(duration.toSeconds()<=60)
                            {
                                customerReviewMasterRes.setReviewTime(duration.toSeconds() + " Sec ago");
                            }
                        }
                        else {
                            customerReviewMasterRes.setReviewTime(duration.toMinutes() + " Min ago");
                        }
                    } else {
                        customerReviewMasterRes.setReviewTime(duration.toHours() + " Hours ago");
                    }
                } else {
                    customerReviewMasterRes.setReviewTime(duration.toDays() + " Days ago");
                }

//                }else {
//                    customerReviewMasterRes.setReviewTime(duration+ "Days ago");
//                }
//                customerReviewMasterRes.setReviewTime(duration.+"Month ago");
                list1.add(customerReviewMasterRes);
            }


//            // Example dates
//            LocalDateTime startDateTime = LocalDateTime.now();
//            LocalDateTime endDateTime = LocalDateTime.now().plusDays(2);
//// Calculate the duration between two dates in terms of hours, minutes, and seconds
//            Duration duration = Duration.between(startDateTime, endDateTime);
//// Output the results
//            System.out.println("Duration: " + duration.toHours() + " hours, " + duration.toMinutes() % 60 + " minutes, " + duration.getSeconds() % 60 + " seconds");

            return list1;
        } else {
            return list1;
        }
    }

    @Override
    public Message addAdminReviewReplyToUser(ReviewReplyRequestDto reviewReplyRequestDto) {
        Message message = new Message();
        Optional<CustomerReviewMaster> customerReviewMasterOptional = customerReviewMasterDao.findById(reviewReplyRequestDto.getCustomerReviewId());
        if (customerReviewMasterOptional.isPresent()) {
            CustomerReviewMaster customerReviewMaster = customerReviewMasterOptional.get();
            List<AdminReviewReply> existingAdminReviewReplies = customerReviewMaster.getAdminReviewReplies();
            if (existingAdminReviewReplies == null) {
                existingAdminReviewReplies = new ArrayList<>();
            }
            List<AdminReviewReply> newAdminReviewReplies = reviewReplyRequestDto.getAdminReviewReplies();
            if (newAdminReviewReplies != null && !newAdminReviewReplies.isEmpty()) {
                AdminReviewReply newReply = newAdminReviewReplies.get(0); // Assuming there's only one element in the list
                if (newReply != null) {
                    existingAdminReviewReplies.addAll(newAdminReviewReplies);
                    customerReviewMaster.setAdminReviewReplies(existingAdminReviewReplies);
                    CustomerReviewMaster updatedCustomerReviewMaster = customerReviewMasterDao.save(customerReviewMaster);
                    message.setFlag(true);
                    message.setMessage("Add Admin reply");
                } else {
                    System.err.println("AdminReviewReply object is null.");
                    message.setFlag(false);
                }
            } else {
                System.err.println("newAdminReviewReplies list is null or empty.");
                message.setFlag(false);
            }
        } else {
            message.setFlag(false);
        }
        return message;
    }

    @Override
    public List<CustomerReviewMaster> todayReviewList() {
        Date date = new Date();
        System.out.println("date..."+date);
        List<CustomerReviewMaster> list = customerReviewMasterDao.findByDateToday(date);
        System.out.println("list..."+list.size());
        return list;
    }

    @Override
    public List<CustomerReviewMaster> YearReviewList(Integer year) {
//        Calendar calendar = Calendar.getInstance();
//        Date date = new Date();
//        calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        System.out.println("yesr.."+year);
        List<CustomerReviewMaster> list=customerReviewMasterDao.findByYear(year);
        System.out.println("list.."+list.size());
        return list;
    }

    @Override
    public List<CustomerReviewMaster> MonthReviewList(Integer month) {
        System.out.println("month"+month);
        List<CustomerReviewMaster> list=customerReviewMasterDao.findByMonth(month);
        System.out.println("list.."+list.size());
        return list;
    }

    @Override
    public Boolean increseLikecount(LikeRequestDto likeRequestDto) {

        CustomerReviewMaster customerReviewMaster=new CustomerReviewMaster();
        Optional<CustomerReviewMaster> customerReviewMaster1=customerReviewMasterDao.findById(likeRequestDto.getCustomerReviewId());
        customerReviewMaster1.ifPresent(settingMaster ->BeanUtils.copyProperties(settingMaster,customerReviewMaster));

        UserMaster userMaster= userDao.getId(likeRequestDto.getUserId());
        if(customerReviewMaster!=null)
        {
          System.out.println("  customer review id ="+customerReviewMaster.getCustomerReviewId());

          if(customerReviewMaster.getUserIds()==null)
          {
            Set<String> userIdsSet=new HashSet<>();
            if(!userIdsSet.contains(userMaster.getUserId())) {
              userIdsSet.add(userMaster.getUserId());
            }
            customerReviewMaster.setUserIds(userIdsSet);
          }else {
            Set<String> userIdsSet=customerReviewMaster.getUserIds();
            if(!userIdsSet.contains(userMaster.getUserId())) {
              userIdsSet.add(userMaster.getUserId());
            }
            customerReviewMaster.setUserIds(userIdsSet);
          }
          if(customerReviewMaster.getLikeCount()==null)
          {
           customerReviewMaster.setLikeCount(0);
          }
            customerReviewMaster.setLikeCount(customerReviewMaster.getLikeCount()+1);
            customerReviewMasterDao.save(customerReviewMaster);
       return true;
        }else
        {
            return false;
        }
    }

    @Override
    public Boolean minusLikecount(LikeRequestDto likeRequestDto) {
        CustomerReviewMaster customerReviewMaster=new CustomerReviewMaster();
        Optional<CustomerReviewMaster> customerReviewMaster1=customerReviewMasterDao.findById(likeRequestDto.getCustomerReviewId());
        customerReviewMaster1.ifPresent(settingMaster ->BeanUtils.copyProperties(settingMaster,customerReviewMaster));

        if(customerReviewMaster!=null)
        {
            Set<String> userIdsSet=customerReviewMaster.getUserIds();
            UserMaster userMaster= userDao.getUserMaster(likeRequestDto.getUserId());
            if(userIdsSet.contains(userMaster.getUserId()))
            {
              do{
                userIdsSet.remove(userMaster.getUserId());
              }while(userIdsSet.contains(userMaster.getUserId()));
            }
            customerReviewMaster.setLikeCount(customerReviewMaster.getLikeCount()-1);
            customerReviewMasterDao.save(customerReviewMaster);
            return true;
        }else
        {
            return false;
        }
    }

  @Override
  public Boolean checkUserLike(LikeRequestDto likeRequestDto) {

    CustomerReviewMaster customerReviewMaster = new CustomerReviewMaster();
    Optional<CustomerReviewMaster> customerReviewMaster1 = customerReviewMasterDao.findById(likeRequestDto.getCustomerReviewId());
    customerReviewMaster1.ifPresent(settingMaster -> BeanUtils.copyProperties(settingMaster, customerReviewMaster));

    if (customerReviewMaster != null) {
        System.out.println(" customerReviewMaster = "+customerReviewMaster.toString());
      Set<String> userIds = new HashSet<>();
      userIds = customerReviewMaster.getUserIds();

      if(userIds==null)
      {
          return false;
      }

      if (userIds.contains(likeRequestDto.getUserId())) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }


//    @Override
//    public List getArtProductIdWiseCustomerReviewMaster(String artProductId) {
//        List list=customerReviewMasterDao.findByArtProductMasterArtProductId(artProductId);
//        return list;
//    }

//    @Override
//    public List getProductIdWiseCustomerReviewMaster(String productId) {
//        List list=customerReviewMasterDao.findByProductMasterProductId(productId);
//        return list;
//    }
}

