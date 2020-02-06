package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.CabinService;
import org.springframework.stereotype.Service;

@Service
public class CabinServiceImpl implements CabinService {
    @Override
    public JSONObject getStatus(String userId) {
        return null;
    }

    @Override
    public JSONObject addNewFish(String userId, String fishCate, int fishNumber) {
        return null;
    }

    @Override
    public JSONObject reduceFish(String userId, String fishCate, int fishNumber) {
        return null;
    }

    @Override
    public JSONObject reduceAll(String userId) {
        return null;
    }
}
