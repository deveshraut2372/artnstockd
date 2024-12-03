package com.zplus.ArtnStockMongoDB.service.impl;

import com.zplus.ArtnStockMongoDB.dao.ArtMasterDao;
import com.zplus.ArtnStockMongoDB.dao.ContributorArtMarkupDao;
import com.zplus.ArtnStockMongoDB.dao.ContributorEarningDao;
import com.zplus.ArtnStockMongoDB.dao.OrientationMasterDao;
import com.zplus.ArtnStockMongoDB.dto.req.ContributorEarningRequestDto;
import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningDateResponseDto;
import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningMonthResponseDto;
import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningResponseDto;
import com.zplus.ArtnStockMongoDB.model.ArtMaster;
import com.zplus.ArtnStockMongoDB.model.ContributorArtMarkupMaster;
import com.zplus.ArtnStockMongoDB.model.ContributorEarning;
import com.zplus.ArtnStockMongoDB.model.OrientationMaster;
import com.zplus.ArtnStockMongoDB.service.ContributorEarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContributorEarningServiceImpl implements ContributorEarningService {
    @Autowired
    public ContributorEarningDao contributorEarningDao;

    @Autowired
    public ArtMasterDao artMasterDao;
    @Autowired
    public ContributorArtMarkupDao contributorArtMarkupDao;
    @Autowired
    private OrientationMasterDao orientationMasterDao;

    //    public void CalculateEarning(ContributorEarningRequestDto contributorEarningRequestDto){
//        double basePrice=0;
//        ArtMaster artMaster= artMasterDao.findByArtId(contributorEarningRequestDto.getArtId()).get();
//
//        ContributorArtMarkupMaster contributorArtMarkupMaster=contributorArtMarkupDao.findByArtMasterArtId(artMaster.getArtId());
//
//        System.out.println("contributorArtMarkupMaster"+contributorArtMarkupMaster.toString());
//        System.out.println("contri..."+contributorArtMarkupMaster.getOrientationMasters().size());
//        for (OrientationMaster orientationMaster:contributorArtMarkupMaster.getOrientationMasters()) {
//            System.out.println("ok..........");
//
//        }
//            ContributorEarning contributorEarning = new ContributorEarning();
//            contributorEarning.setAmount(artMaster.getPrice() * contributorEarningRequestDto.getQuanitiy());
//            contributorEarning.setArtId(contributorEarningRequestDto.getArtId());
//            contributorEarning.setCartArtFrameMaster(contributorEarningRequestDto.getCartArtFrameMaster());
//            contributorEarning.setCartProductMaster(contributorEarningRequestDto.getCartProductMaster());
//            contributorEarning.setDate(LocalDate.now());
//            contributorEarning.setQuantity(contributorEarningRequestDto.getQuanitiy());
//            contributorEarning.setPaymentStatus("Pending");
//            contributorEarning.setUserId(artMaster.getUserMaster().getUserId());
//            contributorEarning.setProductPrice(contributorEarningRequestDto.getProductPrice());
////            contributorEarning.setBasePrice(basePrice);
//            ContributorEarning contributorEarning1 = contributorEarningDao.save(contributorEarning);
//
//
//    }
    public void CalculateEarning(ContributorEarningRequestDto contributorEarningRequestDto) {
        double basePrice = 0;

        ArtMaster artMaster = artMasterDao.findByArtId(contributorEarningRequestDto.getArtId()).get();
        ContributorArtMarkupMaster contributorArtMarkupMaster = contributorArtMarkupDao.findByArtMasterArtId(artMaster.getArtId());

        for (OrientationMaster orientationMaster : contributorArtMarkupMaster.getOrientationMasters()) {
            if (orientationMaster.getShape().equals(artMaster.getOrientation())) {
                System.out.println("shape.." + orientationMaster.getShape());
                System.out.println("shape1.." + artMaster.getOrientation());
                if (orientationMaster.getHeight().equals(artMaster.getHeight()) &&
                        orientationMaster.getWidth().equals(artMaster.getWidth())) {
                    System.out.println("height.." + orientationMaster.getHeight());
                    System.out.println("height1.." + artMaster.getHeight());
                    System.out.println("width.." + orientationMaster.getWidth());
                    System.out.println("width1.." + artMaster.getWidth());
                    basePrice = orientationMaster.getBasePrice();
                    System.out.println("basePrice" + basePrice);
                    break;
                }
            }
        }
        System.out.println("bb" + basePrice);
        if (basePrice == 0) {
            // Handle case where no matching orientation or size is found
            // You can choose to throw an exception or set a default value for basePrice
            // For example, you might set basePrice to a default value:
            basePrice = 0.0; // Replace DEFAULT_BASE_PRICE with your desired default value
        }

        ContributorEarning contributorEarning = new ContributorEarning();
        contributorEarning.setAmount(artMaster.getPrice() * contributorEarningRequestDto.getQuanitiy());
        contributorEarning.setArtId(contributorEarningRequestDto.getArtId());
        contributorEarning.setCartArtFrameMaster(contributorEarningRequestDto.getCartArtFrameMaster());
        contributorEarning.setCartProductMaster(contributorEarningRequestDto.getCartProductMaster());
        contributorEarning.setDate(LocalDate.now());
        contributorEarning.setQuantity(contributorEarningRequestDto.getQuanitiy());
        contributorEarning.setPaymentStatus("Pending");
        contributorEarning.setUserId(artMaster.getUserMaster().getUserId());
        contributorEarning.setProductPrice(contributorEarningRequestDto.getProductPrice());
        contributorEarning.setBasePrice(basePrice);

        ContributorEarning contributorEarning1 = contributorEarningDao.save(contributorEarning);
    }


    @Override
    public Object getAll() {
        return contributorEarningDao.findAll();
    }

    @Override
    public ContributorEarningResponseDto getRecordByUser(String userid) {
        List<ContributorEarning> contributorEarning = contributorEarningDao.findByUserId(userid);
        ContributorEarningResponseDto contributorEarningResponseDto = new ContributorEarningResponseDto();

        contributorEarningResponseDto.setItemSold(contributorEarning.stream().mapToInt(ContributorEarning::getQuantity).sum());
        contributorEarningResponseDto.setEarning(contributorEarning.stream().mapToDouble(ContributorEarning::getAmount).sum());
        // FOR 1 PHASE Art Done so olny consider Arts.
        contributorEarningResponseDto.setArtPrint(contributorEarningResponseDto.getEarning());
        contributorEarningResponseDto.setProductsPrice(contributorEarning.stream().mapToDouble(ContributorEarning::getProductPrice).sum());
        contributorEarningResponseDto.setPaymentStatus("pending");
        contributorEarningResponseDto.setDate(contributorEarning.get(0).getDate());

        List<ContributorEarning> cee = contributorEarning.stream().sorted((o1, o2) -> o1.getDate().compareTo(o2.getDate())).collect(Collectors.toList());
        cee.forEach(System.out::println);
        Map<Month, List<ContributorEarning>> getData = contributorEarning.stream()
                .collect(Collectors.groupingBy(record -> record.getDate().getMonth()));
        getData.forEach((month, contributorEarnings) -> System.out.println(month + "  " + contributorEarnings));

        Map<Month, Double> amout = getData.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(ContributorEarning::getAmount)
                                .sum()));
        Map<Month, Double> quantity = getData.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(ContributorEarning::getQuantity)
                                .sum()));
        Map<Month, Double> productPrice = getData.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .mapToDouble(ContributorEarning::getProductPrice)
                                .sum()));
        Month targetMonth = Month.AUGUST; // Replace with your target month

        Map<Integer, Double> dailySales = cee.stream().filter(sale -> sale.getDate().getMonth() == targetMonth)
                .collect(Collectors.groupingBy(
                        sale -> sale.getDate().getDayOfMonth(),
                        Collectors.summingDouble(ContributorEarning::getAmount)
                ));
        System.out.println("Print daily sale");
        dailySales.forEach((Integer, Double) -> System.out.println(Integer + "  " + Double));
        System.out.println("----------------------------------------");
        List<ContributorEarningMonthResponseDto> contributorEarningMonthResponseDtoList = new ArrayList<>();
        for (Month m : amout.keySet()) {
            ContributorEarningMonthResponseDto cmr = new ContributorEarningMonthResponseDto();

            cmr.setMonth(m);
            cmr.setItemSold(quantity.get(m).intValue());
            cmr.setProductsPrice(productPrice.get(m).doubleValue());
            cmr.setArtPrint(amout.get(m).doubleValue());
            cmr.setEarning(cmr.getArtPrint());
            contributorEarningMonthResponseDtoList.add(cmr);


        }

        contributorEarningMonthResponseDtoList.forEach(System.out::println);
        contributorEarningResponseDto.setContributorEarningMonthResponseDtoList(contributorEarningMonthResponseDtoList);
        return contributorEarningResponseDto;
    }

    @Override
    public ContributorEarningResponseDto getDateWiseData(String userid, String month) {
        List<ContributorEarning> contributorEarning = contributorEarningDao.findByUserId(userid);
        Month targetMonth = Month.AUGUST; // Replace with your target month

        Map<Integer, Double> dailySales = contributorEarning.stream().filter(sale -> sale.getDate().getMonth() == targetMonth)
                .collect(Collectors.groupingBy(
                        sale -> sale.getDate().getDayOfMonth(),
                        Collectors.summingDouble(ContributorEarning::getAmount)
                ));
        System.out.println("Print daily sale");
        dailySales.forEach((Integer, Double) -> System.out.println(Integer + "  " + Double));
        System.out.println("----------------------------------------");
        return null;
    }

    @Override
    public List<Integer> getYear(String userid) {
        contributorEarningDao.findByUserId(userid).stream().forEach(System.out::println);
        List<Integer> year = contributorEarningDao.findByUserId(userid).stream().map(ContributorEarning::getDate).map(s -> s.getYear()).distinct().collect(Collectors.toList());
        return year;
    }

    @Override
    public List<ContributorEarningDateResponseDto> getData(String userid, String month, int year) {
        List<ContributorEarning> contributorEarning = contributorEarningDao.findByUserId(userid);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH);


        Month targetMonth = Month.from(formatter.parse(month));// Replace with your target month
        Year targetYear = Year.of(year);

        YearMonth targetMonthYear = YearMonth.of(year, targetMonth);
        Map<Integer, Double> dailySales = contributorEarning.stream()
                .filter(sale -> YearMonth.from(sale.getDate()).equals(targetMonthYear))
                .collect(Collectors.groupingBy(
                        sale -> sale.getDate().getDayOfMonth(),
                        Collectors.summingDouble(ContributorEarning::getAmount)
                ));
        Map<Integer, Double> dailyqutity = contributorEarning.stream()
                .filter(sale -> YearMonth.from(sale.getDate()).equals(targetMonthYear))
                .collect(Collectors.groupingBy(
                        sale -> sale.getDate().getDayOfMonth(),
                        Collectors.summingDouble(ContributorEarning::getQuantity)
                ));
        Map<Integer, Double> dailyPrice = contributorEarning.stream()
                .filter(sale -> YearMonth.from(sale.getDate()).equals(targetMonthYear))
                .collect(Collectors.groupingBy(
                        sale -> sale.getDate().getDayOfMonth(),
                        Collectors.summingDouble(ContributorEarning::getProductPrice)
                ));
        System.out.println("Print daily sale");
        //    dailySales.forEach((Integer,Double)-> System.out.println(Integer+"  "+Double));
        dailyqutity.forEach((Integer, Double) -> System.out.println(Integer + "  " + Double));
        //     dailyPrice.forEach((Integer,Double)-> System.out.println(Integer+"  "+Double));
        System.out.println("----------------------------------------");
        List<ContributorEarningDateResponseDto> contributorEarningMonthResponseDtoList = new ArrayList<>();

        for (int i : dailySales.keySet()) {
            ContributorEarningDateResponseDto cedr = new ContributorEarningDateResponseDto();
            cedr.setDate(LocalDate.of(year, targetMonth, i));
            cedr.setArtPrint(dailyPrice.get(i).doubleValue());
            cedr.setEarning(dailySales.get(i).doubleValue());
            cedr.setItemSold(dailyqutity.get(i).intValue());
            cedr.setPaymentStatus("Pending");
            cedr.setProductsPrice(dailyPrice.get(i).doubleValue());
            contributorEarningMonthResponseDtoList.add(cedr);
        }

          /*     for (Integer m:dailySales.keySet()){
            ContributorEarningDateResponseDto cmr=new ContributorEarningDateResponseDto();

            cmr.setMonth(dailySales.get(m).intValue());
            cmr.setItemSold(quantity.get(m).intValue());
            cmr.setProductsPrice(productPrice.get(m).doubleValue());
            cmr.setArtPrint(amout.get(m).doubleValue());
            cmr.setEarning(cmr.getArtPrint());
            contributorEarningMonthResponseDtoList.add(cmr);


        }*/
        return contributorEarningMonthResponseDtoList;
    }
}
