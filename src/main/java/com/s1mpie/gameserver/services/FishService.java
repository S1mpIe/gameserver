package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Fish;

public interface FishService {
    JSONObject catchFish(String userId);
    JSONObject goFishing(String userId);
    JSONObject getCabinInventory(String userId);
    JSONObject getWareInventory(String userId);
    JSONObject moveCabinToWare(String userId,int fishId,int number);
    JSONObject moveCabinToWare(String userId);
    JSONObject increaseCabinInventory(String userId);
    JSONObject increaseWareInventory(String userId);
    JSONObject reduceWareInventory(String userId,int fishId,int number);
    JSONObject reduceCabinInventory(String userId,int fishId,int number);
    JSONObject sellWareInventory(String userId,int fishId,int number);
    JSONObject sellCabinInventory(String userId,int fishId,int number);
}
