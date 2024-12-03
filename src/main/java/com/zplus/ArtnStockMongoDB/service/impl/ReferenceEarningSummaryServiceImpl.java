package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ReferenceEarningSummaryDao;
import com.zplus.ArtnStockMongoDB.dao.UserDao;
import com.zplus.ArtnStockMongoDB.dto.req.ReferenceEarningSummaryReqDto;
import com.zplus.ArtnStockMongoDB.dto.req.ReferralEarningReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.ReferenceEarningSummaryResDto;
import com.zplus.ArtnStockMongoDB.dto.res.TotalRefeEarningResDto;
import com.zplus.ArtnStockMongoDB.model.ReferenceEarningSummary;
import com.zplus.ArtnStockMongoDB.model.ReferralEarning;
import com.zplus.ArtnStockMongoDB.model.UserMaster;
import com.zplus.ArtnStockMongoDB.service.ReferenceEarningSummaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
// TAX IS SET BY 20%

@Service
public class ReferenceEarningSummaryServiceImpl implements ReferenceEarningSummaryService {
    @Autowired
    private ReferenceEarningSummaryDao referenceEarningSummaryDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Boolean saveRefe(ReferenceEarningSummaryReqDto referenceEarningSummaryReqDto) {
        UserMaster userMaster = userDao.findByUserId(referenceEarningSummaryReqDto.getUserdata()).get();
        UserMaster clientMaster = userDao.findByUserId(referenceEarningSummaryReqDto.getClientdata()).get();
        System.out.println(userMaster);
        System.out.println(clientMaster);

        ReferenceEarningSummary referenceEarningSummary = new ReferenceEarningSummary();
        BeanUtils.copyProperties(referenceEarningSummaryReqDto, referenceEarningSummary);
        referenceEarningSummary.setUserId(referenceEarningSummaryReqDto.getUserdata());
        referenceEarningSummary.setClientId(referenceEarningSummaryReqDto.getClientdata());
        referenceEarningSummary.setUserdata(userMaster);
        referenceEarningSummary.setClientdata(clientMaster);
        referenceEarningSummary.setFirstPurchase(referenceEarningSummaryReqDto.getFirstPurchase());
        List<ReferralEarning> referralEarning = new ArrayList<>();
        //   List<ReferralEarning> referralEarningfinal=new ArrayList<>();
        List<ReferralEarningReqDto> referralEarningReqDtos = new ArrayList<>();

        referralEarningReqDtos.addAll(referenceEarningSummaryReqDto.getReferralEarningList());

     /*   referralEarningfinal=referralEarning.stream().map(s->{
            s.setEarning((s.getQuatity() * s.getSalePrice())* 0.2);
            return null;
        });*/

        referralEarning.stream().forEach(System.out::println);
        for (ReferralEarningReqDto re : referralEarningReqDtos) {
            ReferralEarning rf = new ReferralEarning();
            Double earning = re.getSalePrice() * re.getQuatity();
            earning = earning * 0.2;
            System.out.println(earning);
            rf.setEarning(earning);
            Double taxred;
            taxred = earning * 0.1;

            System.out.println(taxred);
            rf.setTax(taxred);
            rf.setQuatity(re.getQuatity());
            rf.setPercentageTax(10.00);
            rf.setPercentageEarning(20.00);
            System.out.println("---------------- earning " + earning + " taxred " + taxred);
            rf.setEarningAfterDeduction(earning - taxred);
            System.out.println("loop");

            referralEarning.add(rf);
        }
        referenceEarningSummary.setClientRole(clientMaster.getUserRole().get(0));
        referenceEarningSummary.setReferralEarningList(referralEarning);
        referenceEarningSummaryDao.save(referenceEarningSummary);
        return true;
    }

    @Override
    public TotalRefeEarningResDto getReferenceData(String userid) {
        List<ReferenceEarningSummaryResDto> adddata = new ArrayList<>();
        TotalRefeEarningResDto totalData = new TotalRefeEarningResDto();
        List<ReferenceEarningSummary> res = referenceEarningSummaryDao.findByUserId(userid);
        /*
    private String refereceId;
    private String clientRole;
    private Date registedDate;
    private Date firstPurchase;
    private Double SalePrice;
    private Double TaxDeduction;
    private Double yourEarning;
    private String paymentStatus;
    //private List<ReferralEarning> referralEarningList;
    private String userId;
    private String clientId;
        * */
        Double totalEarnings = 0.0;
        for (ReferenceEarningSummary r : res) {
            ReferenceEarningSummaryResDto referenceEarningSummaryResDto = new ReferenceEarningSummaryResDto();

            referenceEarningSummaryResDto.setRefereceId(r.getRefereceId());
            referenceEarningSummaryResDto.setClientRole(r.getClientRole());
            referenceEarningSummaryResDto.setRegistedDate(r.getRegistedDate());
            referenceEarningSummaryResDto.setFirstPurchase(r.getFirstPurchase());
            referenceEarningSummaryResDto.setSalePrice(r.getSalePrice());
            referenceEarningSummaryResDto.setTaxDeduction(r.getTaxDeduction());
            referenceEarningSummaryResDto.setYourEarning(r.getYourEarning());
            referenceEarningSummaryResDto.setPaymentStatus(r.getPaymentStatus());
            referenceEarningSummaryResDto.setUserId(r.getUserId());
            referenceEarningSummaryResDto.setClientId(r.getClientId());
            referenceEarningSummaryResDto.setPaymentStatus(r.getPaymentStatus());
            referenceEarningSummaryResDto.setClientName(r.getClientdata().getUserFirstName() + " " + r.getClientdata().getUserLastName());
            //   totalEarnings=totalEarnings+r.getYourEarning();
            adddata.add(referenceEarningSummaryResDto);
        }
        totalData.setTotal(totalEarnings);
        totalData.setReferenceEarningSummaries(adddata);
        return totalData;
    }

//    @Override
//    public List<ReferenceEarningSummaryResDto> getReferenceEarningSummaryData(String userId, int month, int year) {
//        List<ReferenceEarningSummaryResDto> tempList=new ArrayList<>();
//        List<ReferenceEarningSummary> list=referenceEarningSummaryDao.findByUserId(userId);
//        System.out.println("list..."+list.size());
//
//        for (ReferenceEarningSummary referenceEarningSummary:list){
//            ReferenceEarningSummaryResDto referenceEarningSummaryResDto =new ReferenceEarningSummaryResDto();
//
//            System.out.println("referenceEarningSummary.getFirstPurchase()..."+referenceEarningSummary.getFirstPurchase());
//            Month month1 = referenceEarningSummary.getFirstPurchase().getMonth();
//            LocalDate localDate = referenceEarningSummary.getFirstPurchase();
//            int yearValue = localDate.getYear();
//            Year year1 = Year.of(yearValue);
//            System.out.println("year1"+year1);
//            System.out.println("month1.."+month1);
//            if (month1.equals(month) && year1.equals(year)){
//                System.out.println("ok....................");
//
//                referenceEarningSummaryResDto.setYourEarning(referenceEarningSummary.getYourEarning());
//                referenceEarningSummaryResDto.setRefereceId(referenceEarningSummary.getRefereceId());
//                referenceEarningSummaryResDto.setRegistedDate(referenceEarningSummary.getRegistedDate());
//                referenceEarningSummaryResDto.setClientName(referenceEarningSummary.getClientdata().getUserFirstName());
//                referenceEarningSummaryResDto.setFirstPurchase(referenceEarningSummary.getFirstPurchase());
//                referenceEarningSummaryResDto.setUserId(referenceEarningSummary.getUserId());
//                referenceEarningSummaryResDto.setPaymentStatus(referenceEarningSummary.getPaymentStatus());
//                referenceEarningSummaryResDto.setTaxDeduction(referenceEarningSummary.getTaxDeduction());
//                referenceEarningSummaryResDto.setSalePrice(referenceEarningSummary.getSalePrice());
//                referenceEarningSummaryResDto.setClientRole(referenceEarningSummary.getClientRole());
//                referenceEarningSummaryResDto.setClientId(referenceEarningSummary.getClientId());
//                tempList.add(referenceEarningSummaryResDto);
//                System.out.println("aa...."+tempList.size());
//            }
//        }
//        System.out.println("tempList.."+tempList.size());
//        return tempList;
//        }

    @Override
    public List<ReferenceEarningSummaryResDto> getReferenceEarningSummaryData(String userId, int month, int year) {
        List<ReferenceEarningSummaryResDto> tempList = new ArrayList<>();
        List<ReferenceEarningSummary> list = referenceEarningSummaryDao.findByUserId(userId);


        for (ReferenceEarningSummary referenceEarningSummary : list) {
            LocalDate firstPurchaseDate = referenceEarningSummary.getFirstPurchase();
            Year year1 = Year.of(firstPurchaseDate.getYear());
            Month month1 = firstPurchaseDate.getMonth();

            if (month1.getValue() == month && year1.getValue() == year) {
                System.out.println("hhh"+referenceEarningSummary.getSalePrice());

                ReferenceEarningSummaryResDto referenceEarningSummaryResDto = new ReferenceEarningSummaryResDto();

                referenceEarningSummaryResDto.setYourEarning(referenceEarningSummary.getYourEarning());
                referenceEarningSummaryResDto.setRefereceId(referenceEarningSummary.getRefereceId());
                referenceEarningSummaryResDto.setRegistedDate(referenceEarningSummary.getRegistedDate());
                referenceEarningSummaryResDto.setClientName(referenceEarningSummary.getClientdata().getUserFirstName());
                referenceEarningSummaryResDto.setFirstPurchase(referenceEarningSummary.getFirstPurchase());
                referenceEarningSummaryResDto.setUserId(referenceEarningSummary.getUserId());
                referenceEarningSummaryResDto.setPaymentStatus(referenceEarningSummary.getPaymentStatus());
                referenceEarningSummaryResDto.setTaxDeduction(referenceEarningSummary.getTaxDeduction());
                referenceEarningSummaryResDto.setSalePrice(referenceEarningSummary.getSalePrice());
                referenceEarningSummaryResDto.setClientRole(referenceEarningSummary.getClientRole());
                referenceEarningSummaryResDto.setClientId(referenceEarningSummary.getClientId());

                tempList.add(referenceEarningSummaryResDto);
            }
        }

        return tempList;
    }
}
