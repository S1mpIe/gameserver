package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Hunt;
import com.s1mpie.gameserver.repostiory.HuntMapper;
import com.s1mpie.gameserver.services.FishService;
import com.s1mpie.gameserver.services.HuntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class HuntServiceImpl implements HuntService {
    @Autowired
    private HuntMapper huntMapper;
    @Autowired
    private FishService fishService;
    @Override
    public JSONObject getLastBeginTime(String userId) {
        System.out.println(userId);
        Hunt lastTime = huntMapper.getLastActiveTime(userId);
        System.out.println(lastTime);
        JSONObject jsonObject = new JSONObject();
        if(lastTime != null){
            jsonObject.put("time",lastTime);
        }else {
            jsonObject.put("status","null");
        }
        return jsonObject;
    }

    @Override
    public JSONObject startCatch(String userId) {
        return fishService.catchFish(userId);
    }

    @Override
    public JSONObject startShip(String userId) {
        Hunt lastActiveTime = huntMapper.getLastActiveTime(userId);
        int huntId ;
        JSONObject jsonObject = new JSONObject();
        if(lastActiveTime == null){
            Hunt lastTime = huntMapper.getLastTime(userId);
            if(lastTime == null) {
                huntId = 0;
            } else {
                huntId = lastTime.getHuntId();
            }
            huntMapper.startShip(userId,huntId + 1,new Timestamp(System.currentTimeMillis()));
            jsonObject.put("status","ok");
        }else {
            jsonObject.put("status","error");
            jsonObject.put("cause","ship is active...");
        }
        return jsonObject;
    }

    @Override
    public JSONObject stopShip(String userId) {
        JSONObject jsonObject = new JSONObject();
        Hunt lastActiveTime = huntMapper.getLastActiveTime(userId);
        if(lastActiveTime == null){
            jsonObject.put("status","error");
            jsonObject.put("cause","ship has stopped...");
        }else {
            huntMapper.stopShip(userId,lastActiveTime.getHuntId());
            fishService.goFishing(userId);
            jsonObject.put("status","ok");
        }
        return jsonObject;
    }
}
