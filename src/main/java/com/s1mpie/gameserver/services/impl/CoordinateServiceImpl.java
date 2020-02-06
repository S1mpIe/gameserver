package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Coordinate;
import com.s1mpie.gameserver.repostiory.CoordinateMapper;
import com.s1mpie.gameserver.services.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordinateServiceImpl implements CoordinateService {
    @Autowired
    private CoordinateMapper coordinateMapper;
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
}
