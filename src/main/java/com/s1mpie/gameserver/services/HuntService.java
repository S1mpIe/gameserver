package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
public interface HuntService {
    JSONObject getLastBeginTime(String userId);
    JSONObject startShip(String userId);
    JSONObject stopShip(String userId);
}
