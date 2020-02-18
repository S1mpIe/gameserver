package com.s1mpie.gameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.CoordinateService;
import com.s1mpie.gameserver.services.InitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/init")
public class InitController {
    @Autowired
    private InitService initService;

    @RequestMapping("/map")
    @ResponseBody
    public JSONObject getMap(@RequestParam("userId")String userId){
        return initService.getMap(userId);
    }

    @RequestMapping("/sign")
    @ResponseBody
    public JSONObject getNewPower(@RequestParam("userId")String userId){
        return initService.signIn(userId);
    }

    @RequestMapping("/accessiblePower")
    @ResponseBody
    public JSONObject getAccessiblePower(@RequestParam("userId")String userId){
        return initService.getAccessiblePower(userId);
    }

    @RequestMapping("/obtain")
    @ResponseBody
    public JSONObject obtainPower(@RequestParam("userId")String userId,@RequestParam("powerName")String name){
        return initService.obtainPower(userId, name);
    }

    @RequestMapping("/addTrip")
    @ResponseBody
    public JSONObject addNewTrip(@RequestParam("userId")String userId){
        return initService.addRandomTrip(userId);
    }
    @RequestMapping("/addPay")
    @ResponseBody
    public JSONObject addNewPay(@RequestParam("userId")String userId){
        return initService.addRandomPay(userId);
    }
}
