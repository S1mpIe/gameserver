package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.PropService;
import org.springframework.stereotype.Service;

@Service
public class PropServiceImpl implements PropService {

    @Override
    public JSONObject getAllGoods(String userId) {
        return null;
    }

    @Override
    public JSONObject getBagInventory(String userId) {
        return null;
    }

    @Override
    public JSONObject buyProp(String userId, int propId) {
        return null;
    }

    @Override
    public JSONObject useProp(String userId, int propId) {
        return null;
    }
}
