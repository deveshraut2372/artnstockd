package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.ReferenceEarningSummaryReqDto;
import com.zplus.ArtnStockMongoDB.dto.res.ReferenceEarningSummaryResDto;
import com.zplus.ArtnStockMongoDB.dto.res.TotalRefeEarningResDto;
import com.zplus.ArtnStockMongoDB.model.ReferenceEarningSummary;

import java.util.List;

public interface ReferenceEarningSummaryService {
    Boolean saveRefe(ReferenceEarningSummaryReqDto referenceEarningSummaryReqDto);

    TotalRefeEarningResDto getReferenceData(String userid);

    List<ReferenceEarningSummaryResDto> getReferenceEarningSummaryData(String userId, int month, int year);
}
