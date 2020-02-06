package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


public interface CabinService {
    JSONObject getStatus(String userId);
    JSONObject addNewFish(String userId,String fishCate,int fishNumber);
    JSONObject reduceFish(String userId,String fishCate,int fishNumber);
    JSONObject reduceAll(String userId);
}
