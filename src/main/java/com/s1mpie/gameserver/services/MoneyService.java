package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

public interface MoneyService {
    JSONObject getCurrentMoney(String userId);
    JSONObject addMoney(String userId,int money);
    JSONObject reduceMoney(String userId,int money);
}
