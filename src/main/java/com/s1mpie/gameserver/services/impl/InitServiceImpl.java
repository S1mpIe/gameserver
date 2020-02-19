package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.AccessiblePower;
import com.s1mpie.gameserver.model.WaterPiece;
import com.s1mpie.gameserver.repostiory.CoordinateMapper;
import com.s1mpie.gameserver.services.InitService;
import com.s1mpie.gameserver.services.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class InitServiceImpl implements InitService {
    @Autowired
    private CoordinateMapper coordinateMapper;
    @Autowired
    private PowerService powerService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JSONObject getMap(String userId) {
        WaterPiece[] waterPieces = coordinateMapper.queryWholeMap(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("map",waterPieces);
        return jsonObject;
    }

    @Override
    public JSONObject signIn(String userId) {
        Date sign = (Date) redisTemplate.opsForHash().get("sign", userId);
        JSONObject jsonObject = new JSONObject();
        if(sign == null){
            redisTemplate.opsForHash().put("sign",userId,new Date(System.currentTimeMillis()));
            jsonObject.put("status","success");
            powerService.increaseCurrentPower(userId,4);
        }else {
            int day = sign.getDay();
            Date today = new Date(System.currentTimeMillis());
            if(today.getDay() - day >= 1){
                jsonObject.put("status","success");
                jsonObject.put("power",4);
                redisTemplate.opsForHash().put(sign,userId,new Date(System.currentTimeMillis()));
                redisTemplate.opsForList().leftPushAll("accessiblePower-" + userId,new AccessiblePower("签到",4,new Date(System.currentTimeMillis())));
            }else {
                jsonObject.put("status","failed");
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getAccessiblePower(String userId) {
        JSONObject jsonObject = new JSONObject();
        String key = "accessiblePower-" + userId;
        List<AccessiblePower> range = redisTemplate.opsForList().range(key, 0, -1);
        jsonObject.put("powerArr",range);
        return jsonObject;
    }

    @Override
    public JSONObject obtainPower(String userId, String powerName) {
        JSONObject jsonObject = new JSONObject();
        List<AccessiblePower> range = redisTemplate.opsForList().range("accessiblePower-" + userId, 0, -1);
        int size = range.size();
        boolean flag = false;
        for(int i = 0;i < size;i++){
            AccessiblePower power = range.get(i);
            if(power.getName().equals(powerName)){
                range.remove(i);
                powerService.increaseCurrentPower(userId,power.getNumber());
                flag = true;
                break;
            }
        }
        if (flag){
            jsonObject.put("status","success");
            redisTemplate.delete("accessiblePower-" + userId);
            if (!range.isEmpty()) {
                redisTemplate.opsForList().leftPushAll("accessiblePower-" + userId,range);
            }
        }else {
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","未找到对应行动力");
        }
        return jsonObject;
    }

    @Override
    public JSONObject addRandomTrip(String userId) {
        String key = "accessiblePower-" + userId;
        JSONObject jsonObject = new JSONObject();
        Date date = new Date(System.currentTimeMillis());
        List<AccessiblePower> range = redisTemplate.opsForList().range(key, 0, -1);
        int number = 0;
        for(AccessiblePower accessiblePower:range){
            if(accessiblePower.getBirthDay().getDay() == date.getDay() && accessiblePower.getName().equals("乘车")){
                number ++;
            }
        }
        if(number >= 2){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","今日已满");
        }{
            redisTemplate.opsForList().leftPush(key,new AccessiblePower("乘车",3,date));
            jsonObject.put("status","success");
        }
        return jsonObject;
    }

    @Override
    public JSONObject addRandomPay(String userId) {
        String key = "accessiblePower-" + userId;
        JSONObject jsonObject = new JSONObject();
        Date date = new Date(System.currentTimeMillis());
        List<AccessiblePower> range = redisTemplate.opsForList().range(key, 0, -1);
        int number = 0;
        for(AccessiblePower accessiblePower:range){
            if(accessiblePower.getBirthDay().getDay() == date.getDay() && accessiblePower.getName().equals("支付")){
                number ++;
            }
        }
        if(number >= 1){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","今日已满");
        }{
            redisTemplate.opsForList().leftPush(key,new AccessiblePower("支付",3,date));
            jsonObject.put("status","success");
        }
        return jsonObject;
    }
}
