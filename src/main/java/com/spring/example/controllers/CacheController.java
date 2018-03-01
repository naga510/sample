package com.spring.example.controllers;

import com.spring.example.models.CacheData;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CacheController
{
    private Map<String,CacheData> cacheDataMap = Collections.EMPTY_MAP;

    @PostConstruct
    @Scheduled(cron = "${cache.refreshTimeinMinutes} * * * * *")
    public void loadCache() {
        //TODO:Replace with Xml load to list
        List<CacheData> cacheDataList=new ArrayList(){{
            add(new CacheData("123","value1", Date.from(Instant.now())));
            add(new CacheData("456","value2", Date.from(Instant.now())));
        }};

        cacheDataMap = cacheDataList.stream().collect(Collectors.toConcurrentMap(c->c.getKey(),c->c));
    }
    @RequestMapping("/rest/cache/{key}")
    public CacheData getValue(@PathVariable String key, HttpServletResponse response)
    {
        CacheData value= cacheDataMap.get(key);
        if(null == value) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        else {
            return value;
        }
    }



}
