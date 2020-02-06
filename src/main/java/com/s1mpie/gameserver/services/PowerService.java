package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


public interface PowerService {
    JSONObject getStatus(String userId);
    JSONObject updateMaxPower(String userId,int power);
    JSONObject updateCurrentPower(String userId,int power);
}
