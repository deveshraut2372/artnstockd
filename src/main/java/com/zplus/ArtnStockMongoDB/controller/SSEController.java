package com.zplus.ArtnStockMongoDB.controller;

import com.zplus.ArtnStockMongoDB.dao.UserDao;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


import java.time.Duration;
import java.util.List;

// server sent event
@RestController
@CrossOrigin(origins = "*")
public class SSEController {


   private  UserDao userDao;

    public SSEController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/events")
    public Flux<ServerSentEvent<String>> getEvents() {
        return Flux.interval(Duration.ofSeconds(3))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("message")
                        .data("loginCount " + getLoginUserCount())
                        .build())
                .log();
    }

    public Integer getLoginUserCount()
    {
        List list=userDao.getcountsByLogin(true);
        return list.size();
    }
}