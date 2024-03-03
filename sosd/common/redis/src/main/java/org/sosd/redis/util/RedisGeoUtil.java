package org.sosd.redis.util;


import org.springframework.data.geo.*;
import org.springframework.data.redis.core.RedisTemplate;

// 缓存处理经纬度
public class RedisGeoUtil {

    private static RedisTemplate<String, String> redisTemplate;


    // 添加地理位置
    public void addLocation(String key, double longitude, double latitude, String member) {
        redisTemplate.opsForGeo().add(key, new Point(longitude, latitude), member);
    }

    // 获取地理位置
    public Point getLocation(String key, String member) {
        return redisTemplate.opsForGeo().position(key, member).stream().findFirst().orElse(null);
    }

}
