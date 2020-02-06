package com.s1mpie.gameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/coordinate")
public class CoordinateController {
    @Autowired
    private CoordinateService coordinateService;

    @RequestMapping("/getCurrent")
    @ResponseBody
    public JSONObject getCurrentCoordinate(@RequestParam("userId")String userId){
        return coordinateService.getCoordinate(userId);
    }
}
