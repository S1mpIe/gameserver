package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.WaterPiece;
import com.s1mpie.gameserver.repostiory.CoordinateMapper;
import com.s1mpie.gameserver.services.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitServiceImpl implements InitService {
    @Autowired
    private CoordinateMapper coordinateMapper;
    @Override
    public JSONObject getMap(String userId) {
        WaterPiece[] waterPieces = coordinateMapper.queryWholeMap(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("map",waterPieces);
        return jsonObject;
    }
}
