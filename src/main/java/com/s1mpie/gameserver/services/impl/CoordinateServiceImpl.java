package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Coordinate;
import com.s1mpie.gameserver.model.Powers;
import com.s1mpie.gameserver.model.WaterPiece;
import com.s1mpie.gameserver.repostiory.CoordinateMapper;
import com.s1mpie.gameserver.repostiory.PowerMapper;
import com.s1mpie.gameserver.services.CoordinateService;
import com.s1mpie.gameserver.services.PowerService;
import com.s1mpie.gameserver.utils.UniversalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordinateServiceImpl implements CoordinateService {
    @Autowired
    private CoordinateMapper coordinateMapper;
    @Autowired
    private PowerMapper powerMapper;
    @Autowired
    private PowerService powerService;
    @Autowired
    private UniversalUtil universalUtil;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JSONObject updateCoordinate(String userId, int x, int y) {
        return null;
    }

    @Override
    public JSONObject getCoordinate(String userId) {
        Coordinate coordinate = coordinateMapper.queryCurrent(userId);
        JSONObject jsonObject = new JSONObject();
        if(coordinate == null){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","用户不存在");
        }else {
            jsonObject.put("x",coordinate.getX());
            jsonObject.put("y",coordinate.getY());
        }
        return jsonObject;
    }

    @Override
    public JSONObject getPath(String userId,int disX,int disY) {
        WaterPiece[] waterPieces = coordinateMapper.queryWholeMap(userId);
        Coordinate coordinate = coordinateMapper.queryCurrent(userId);
        WaterPiece[] path = universalUtil.getPath(waterPieces,coordinate,userId, disX, disY);
        JSONObject jsonObject = new JSONObject();
        if(path == null || path[0] == null){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","无法到达");
        }else{
            if(redisTemplate.hasKey("path-" + userId)){
                redisTemplate.delete("path-" + userId);
            }
            redisTemplate.opsForList().leftPushAll("path-" + userId,path);
            Coordinate[] coorPath = new Coordinate[path.length];
            int index = 0;
            for(WaterPiece waterPiece:path){
                if (waterPiece != null) {
                    coorPath[index++] = new Coordinate(waterPiece.getX(),waterPiece.getY());
                }
            }
            jsonObject.put("path",coorPath);
        }
        return jsonObject;
    }

    @Override
    public JSONObject move(String userId, int disX, int disY) {
        List<WaterPiece> range = redisTemplate.opsForList().range("path-" + userId, 0, -1);
        int totalPower = 0;
        int index = 0;
        for (WaterPiece waterPiece:range
             ) {
            if (waterPiece != null && index != 0){
                totalPower += waterPiece.getPowerCost();
                index ++;
            }
        }
        return powerService.reduceCurrentPower(userId,totalPower);
    }
}
