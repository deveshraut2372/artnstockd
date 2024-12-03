package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningDateResponseDto;
import com.zplus.ArtnStockMongoDB.dto.res.ContributorEarningResponseDto;

import java.util.List;

public interface ContributorEarningService {

    Object getAll();

    ContributorEarningResponseDto getRecordByUser(String userid);

    ContributorEarningResponseDto getDateWiseData(String userid, String month);

    List<Integer> getYear(String userid);

    List<ContributorEarningDateResponseDto> getData(String userid, String month, int year);
}
