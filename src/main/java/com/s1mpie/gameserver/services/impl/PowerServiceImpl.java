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
        return null;
    }

    @Override
    public JSONObject updateCurrentPower(String userId, int power) {
        return null;
    }
}
