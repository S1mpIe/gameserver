package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;

public interface PropService {
    JSONObject getAllGoods(String userId);
    JSONObject getBagInventory(String userId);
    JSONObject getAllBuff(String userId);
    JSONObject buyProp(String userId,int propId,int number);
    JSONObject useProp(String userId,int propId,int number);
}
