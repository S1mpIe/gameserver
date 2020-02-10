package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


public interface PowerService {
    JSONObject getStatus(String userId);
    JSONObject updateMaxPower(String userId,int power);
    JSONObject reduceMaxPower(String userId,int power);
    JSONObject increaseMaxPower(String userId,int power);
    JSONObject updateCurrentPower(String userId,int power);
    JSONObject reduceCurrentPower(String userId,int power);
    JSONObject increaseCurrentPower(String userId,int power);
}
