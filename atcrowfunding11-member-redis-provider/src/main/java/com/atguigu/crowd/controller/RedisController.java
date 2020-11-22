package com.atguigu.crowd.controller;

import com.atguigu.crowd.util.ResultEntity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class RedisController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @RequestMapping("/set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value
    ){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key,value);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }

    }

    @RequestMapping("/set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeOut(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") long time,
            @RequestParam("timeutil") TimeUnit timeutil
    ){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(key,value,time,timeutil);
            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/get/redis/string/value/bykey")
    ResultEntity<String> getRedisStringValueByKeyRemote(@RequestParam("key") String key){
        try {
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            String str = operations.get(key);
            return ResultEntity.successWithData(str);
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key){
        try {
            stringRedisTemplate.delete(key);

            return ResultEntity.successWithoutData();
        }catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }
}
