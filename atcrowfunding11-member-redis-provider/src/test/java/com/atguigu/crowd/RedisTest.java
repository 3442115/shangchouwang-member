package com.atguigu.crowd;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest(classes = MainRedis3000.class)
public class RedisTest {
    //  此时的对象为空不知道为何
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void get(){
        System.out.println(stringRedisTemplate);
    }
}
