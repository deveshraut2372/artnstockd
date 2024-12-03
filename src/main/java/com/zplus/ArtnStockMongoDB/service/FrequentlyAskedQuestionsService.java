package com.zplus.ArtnStockMongoDB.service;

import com.zplus.ArtnStockMongoDB.dto.req.FrequentlyAskedQuestionsReqDto;
import com.zplus.ArtnStockMongoDB.model.FrequentlyAskedQuestionsMaster;

import java.util.List;

public interface FrequentlyAskedQuestionsService {
    Boolean saveFrequentlyAskedQuestions(FrequentlyAskedQuestionsReqDto frequentlyAskedQuestionsReqDto);

    Boolean updateFrequentlyAskedQuestions(FrequentlyAskedQuestionsReqDto frequentlyAskedQuestionsReqDto);

    List<FrequentlyAskedQuestionsMaster> getAllListFrequentlyAskedQuestions();

    FrequentlyAskedQuestionsMaster getByFaqId(String faqId);

    List getActiveListFrequentlyAskedQuestions();

    List<FrequentlyAskedQuestionsMaster> getTypeWiseFaq(String type);

    Boolean DeleteByFaqId(String faqId);
}
