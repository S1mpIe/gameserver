package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Fish;
import com.s1mpie.gameserver.model.WaterPiece;
import com.s1mpie.gameserver.repostiory.CoordinateMapper;
import com.s1mpie.gameserver.repostiory.FishMapper;
import com.s1mpie.gameserver.services.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FishServiceImpl implements FishService {
    @Autowired
    private FishMapper fishMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CoordinateMapper coordinateMapper;
    @Override
    public JSONObject catchFish(String userId) {
        Fish[] fish = fishMapper.queryAllCate();
        JSONObject jsonObject = new JSONObject();
        WaterPiece waterPiece = coordinateMapper.queryCurrentPiece(userId);
        jsonObject.put("status","failed");
        int asInt = ThreadLocalRandom.current().ints(1, 1, 100).findFirst().getAsInt();
        Fish ans = new Fish();
        int len = fish.length;
        double midPro = 0;
        for(int i = 0;i < len;i++){
            double pro = waterPiece.getBuff() * fish[i].getProbability() * 100;
            if(pro + midPro >= asInt && midPro <= asInt){
                ans = fish[i];
                ans.setNumber(ans.getNumber() + 1);
                break;
            }else {
                midPro += pro;
            }
        }
        Long size = redisTemplate.opsForList().size("cabin-" + userId);
        if(size >= 3){
            jsonObject.put("errmsg","已满");
        }else {
            if (!ans.getName().equals("无")) {
                ans.setProbability(0);
                System.out.println(redisTemplate);
                redisTemplate.opsForList().leftPush("cabin-" + userId,ans);
                jsonObject.put("status","success");
            }else {
                jsonObject.put("errmsg","未捕捞到");
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject goFishing(String userId) {
        Fish[] fish = fishMapper.queryAllCate();
        JSONObject jsonObject = new JSONObject();
        WaterPiece waterPiece = coordinateMapper.queryCurrentPiece(userId);
        int asInt = ThreadLocalRandom.current().ints(1, 1, 100).findFirst().getAsInt();
        Fish ans = new Fish();
        double midPro = 0;
        int len = fish.length;
        for(int i = 0;i < len;i++){
            double pro = waterPiece.getBuff() * fish[i].getProbability() * 100;
            if(pro + midPro >= asInt && midPro <= asInt){
                ans = fish[i];
                break;
            }else {
                midPro += pro;
            }
        }
        jsonObject.put("status","failed");
        if (!ans.getName().equals("无")) {
            Fish queryFish = fishMapper.queryWareSimple(userId, ans.getId());
            if(queryFish == null){
                fishMapper.insertFish(userId,ans.getId(),1);
            }else {
                fishMapper.updateWareHouse(userId,queryFish.getId(),queryFish.getNumber() + 1);
            }
            jsonObject.put("status","success");
        }
        return jsonObject;
    }

    @Override
    public JSONObject getCabinInventory(String userId) {
        List<Fish> cabin = redisTemplate.opsForList().range("cabin-" + userId, 0, -1);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cabin",cabin);
        return jsonObject;
    }

    @Override
    public JSONObject getWareInventory(String userId) {
        Fish[] fishes = fishMapper.queryWareHouse(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("warehouse",fishes);
        return jsonObject;
    }

    @Override
    public JSONObject moveCabinToWare(String userId, int fishId, int number) {
        return null;
    }

    @Override
    public JSONObject moveCabinToWare(String userId) {
        return null;
    }

    @Override
    public JSONObject increaseCabinInventory(String userId) {
        return null;
    }

    @Override
    public JSONObject increaseWareInventory(String userId) {
        return null;
    }

    @Override
    public JSONObject reduceWareInventory(String userId, int fishId, int number) {
        return null;
    }

    @Override
    public JSONObject reduceCabinInventory(String userId, int fishId, int number) {
        return null;
    }

    @Override
    public JSONObject sellWareInventory(String userId, int fishId, int number) {
        return null;
    }

    @Override
    public JSONObject sellCabinInventory(String userId, int fishId, int number) {
        return null;
    }
}
