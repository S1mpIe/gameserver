package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;

public interface InitService {
    JSONObject getMap(String userId);
    JSONObject signIn(String userId);
    JSONObject getAccessiblePower(String userId);
    JSONObject obtainPower(String userId,String powerName);
    JSONObject addRandomTrip(String userId);
    JSONObject addRandomPay(String userId);
}
