package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Money;
import com.s1mpie.gameserver.repostiory.MoneyMapper;
import com.s1mpie.gameserver.services.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyServiceImpl implements MoneyService {
    @Autowired
    private MoneyMapper moneyMapper;
    @Override
    public JSONObject getCurrentMoney(String userId) {
        Money money = moneyMapper.queryCurrent(userId);
        JSONObject jsonObject = new JSONObject();
        if(money == null){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","用户不存在");
        }else {
            jsonObject.put("money",money.getCurrent());
        }
        return jsonObject;
    }

    @Override
    public JSONObject addMoney(String userId, int money) {
        Money money1 = moneyMapper.queryCurrent(userId);
        int i = moneyMapper.updateCurrent(userId, money1.getCurrent() + money);
        JSONObject jsonObject = new JSONObject();
        if(i == 1){
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status","failed");
        }
        return jsonObject;
    }

    @Override
    public JSONObject reduceMoney(String userId, int money) {
        return null;
    }
}
