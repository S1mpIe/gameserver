package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Powers;
import com.s1mpie.gameserver.repostiory.PowerMapper;
import com.s1mpie.gameserver.services.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PowerServiceImpl implements PowerService {
    @Autowired
    private PowerMapper powerMapper;

    @Override
    public JSONObject getStatus(String userId) {
        Powers powers = powerMapper.queryCurrent(userId);
        JSONObject jsonObject = new JSONObject();
        if (powers == null){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","用户不存在");
        }else {
            jsonObject.put("max",powers.getMax());
            jsonObject.put("current",powers.getCurrent());
        }
        return jsonObject;
    }
    @Override
    public JSONObject updateMaxPower(String userId, int power) {
        JSONObject jsonObject = new JSONObject();
        int number = powerMapper.updateMax(userId, power);
        if(number == 1){
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status","failed");
        }
        return jsonObject;
    }

    @Override
    public JSONObject reduceMaxPower(String userId, int power) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","failed");
        Powers powers = powerMapper.queryCurrent(userId);
        if(power >= 0 && powers.getMax() >= power){
            return updateMaxPower(userId,powers.getMax() - power);
        }
        return jsonObject;
    }

    @Override
    public JSONObject increaseMaxPower(String userId, int power) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","failed");
        Powers powers = powerMapper.queryCurrent(userId);
        if(power >= 0){
            return updateMaxPower(userId,powers.getMax() + power);
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateCurrentPower(String userId, int power) {
        JSONObject jsonObject = new JSONObject();
        int i = powerMapper.updateCurrent(userId, power);
        if(i == 1){
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status","failed");
        }
        return jsonObject;
    }

    @Override
    public JSONObject reduceCurrentPower(String userId, int power) {
        JSONObject jsonObject = new JSONObject();
        Powers powers = powerMapper.queryCurrent(userId);
        jsonObject.put("status","failed");
        if(powers.getCurrent() >= power && power >= 0){
            return updateCurrentPower(userId,powers.getCurrent() - power);
        }else {
            jsonObject.put("errmsg","体力不足");
        }
        return jsonObject;
    }

    @Override
    public JSONObject increaseCurrentPower(String userId, int power) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","failed");
        Powers powers = powerMapper.queryCurrent(userId);
        if(power >= 0){
            return updateCurrentPower(userId,powers.getCurrent() + power);
        }
        return jsonObject;
    }
}
