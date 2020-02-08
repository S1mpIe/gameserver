package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


public interface CoordinateService {
    JSONObject updateCoordinate(String userId,int x,int y);
    JSONObject getCoordinate(String userId);
    JSONObject getPath(String userId,int disX,int disY);
    JSONObject move(String userId,int disX,int disY);
}
