package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Coordinate;
import com.s1mpie.gameserver.model.Fish;
import com.s1mpie.gameserver.model.WaterPiece;
import com.s1mpie.gameserver.repostiory.CoordinateMapper;
import com.s1mpie.gameserver.repostiory.FishMapper;
import com.s1mpie.gameserver.services.FishService;
import com.s1mpie.gameserver.services.MoneyService;
import com.s1mpie.gameserver.services.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class FishServiceImpl implements FishService {
    @Autowired
    private FishMapper fishMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PowerService powerService;
    @Autowired
    private CoordinateMapper coordinateMapper;
    @Autowired
    private MoneyService moneyService;
    @Override
    public JSONObject catchFish(String userId) {
        JSONObject jsonObject = new JSONObject();
        Long size = redisTemplate.opsForList().size("cabin-" + userId);
        if(size >= 3){
            jsonObject.put("errmsg","已满");
        }else {
            if (powerService.reduceCurrentPower(userId,3).getString("status").equals("success")){
                Fish[] fish = fishMapper.queryAllCate();
                WaterPiece waterPiece = coordinateMapper.queryCurrentPiece(userId);
                jsonObject.put("status","failed");
                int asInt = ThreadLocalRandom.current().nextInt(0,100);
                Fish ans = new Fish();
                int len = fish.length;
                double midPro = 0;
                for(int i = 0;i < len;i++){
                    double pro = waterPiece.getBuff() * fish[i].getProbability() * 100;
                    System.out.println(fish[i].getName() + "-" + midPro + "-" + pro + "-" + asInt);
                    if(pro + midPro >= asInt && midPro <= asInt){
                        ans = fish[i];
                        ans.setNumber(ans.getNumber() + 1);
                        break;
                    }else {
                        midPro += pro;
                    }
                }
                if (!ans.getName().equals("无")) {
                    ans.setProbability(0);;
                    redisTemplate.opsForList().leftPush("cabin-" + userId,ans);
                    jsonObject.put("fish",ans);
                    jsonObject.put("status","success");
                }else {
                    jsonObject.put("errmsg","未捕捞到");
                }
            }else {
                jsonObject.put("status","failed");
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
            increaseWareInventory(userId,ans.getId(),1);
            jsonObject.put("status","success");
            jsonObject.put("fish",ans);
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
    public JSONObject moveCabinToWare(String userId, int fishId) {
        List<Fish> range = redisTemplate.opsForList().range("cabin-" + userId, 0, -1);
        int size = range.size();
        List<Fish> newList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject();
        Coordinate coordinate = coordinateMapper.queryCurrent(userId);
        int x = coordinate.getX();
        int y = coordinate.getY();
        if(!((x == 2 && y == 3) || (x == 3 && (y == 2 || y == 4)) || (x == 4 && y == 3))){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","未到达指定位置");
            return jsonObject;
        }
        Iterator<Fish> iterator = range.iterator();
        int flag = 1;
        while (iterator.hasNext()){
            Fish next = iterator.next();
            if(next.getId() == fishId && flag == 1){
                jsonObject = increaseWareInventory(userId,fishId,next.getNumber());
                flag = 0;
                continue;
            }
            newList.add(next);
        }
        redisTemplate.delete("cabin-" + userId);
        if(!newList.isEmpty()){
            redisTemplate.opsForList().leftPushAll("cabin-" + userId,newList);
        }
        return jsonObject;
    }

    @Override
    public JSONObject moveCabinToWare(String userId) {
        List<Fish> range = redisTemplate.opsForList().range("cabin-" + userId, 0, -1);
        JSONObject jsonObject = new JSONObject();
        Coordinate coordinate = coordinateMapper.queryCurrent(userId);
        int x = coordinate.getX();
        int y = coordinate.getY();
        if((x != 2 && y != 3) && (x != 3 && y != 2) && (x != 3 && y != 4) && (x != 4 && y != 3)){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","未到达指定位置");
            return jsonObject;
        }
        Iterator<Fish> iterator = range.iterator();
        while (iterator.hasNext()){
            Fish next = iterator.next();
            jsonObject = increaseWareInventory(userId,next.getId(),next.getNumber());
        }
        redisTemplate.delete("cabin-" + userId);
        if(jsonObject.isEmpty()){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","船舱为空");
        }
        return jsonObject;
    }

    @Override
    public JSONObject increaseCabinInventory(String userId,int fishId,int number) {
        return null;
    }

    @Override
    public JSONObject increaseWareInventory(String userId, int fishId, int number) {
        Fish fish = fishMapper.queryWareSimple(userId,fishId);
        int index;
        if(fish == null){
            index = fishMapper.insertFish(userId,fishId,number);
        }else {
            index = fishMapper.updateWareHouse(userId,fishId,fish.getNumber() + number);
        }
        JSONObject jsonObject = new JSONObject();
        System.out.println(index + "-" + fish.toString());
        if(index == 1){
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status","failed");
        }
        return jsonObject;
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
        Fish fish = fishMapper.queryWareSimple(userId,fishId);
        JSONObject jsonObject = new JSONObject();
        Coordinate coordinate = coordinateMapper.queryCurrent(userId);
        int x = coordinate.getX();
        int y = coordinate.getY();
        if(fish == null || fish.getNumber() < number){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","数量超限或库存不存在");
        }else if((x != 2 && y != 3) &&(x != 3 && y != 2 && y != 3 && y != 4) && (x != 4 && y != 3)){
            jsonObject.put("status","failed");
            jsonObject.put("status","未到达指定位置");
        }else {
            fishMapper.updateWareHouse(userId,fishId,fish.getNumber() - number);
            jsonObject = moneyService.addMoney(userId, (int) (fish.getPrice() * number));
        }
        if (jsonObject.getString("status").equals("success")){
            coordinateMapper.updateUserMap(userId,ThreadLocalRandom.current().nextInt(1,10));
        }
        return jsonObject;
    }

    @Override
    public JSONObject sellCabinInventory(String userId, int fishId, int number) {
        return null;
    }
}
