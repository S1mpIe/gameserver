package com.s1mpie.gameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ship")
public class ShipController {
    @Autowired
    private FishService fishService;

    @RequestMapping("/getWare")
    @ResponseBody
    public JSONObject getWareInven(@RequestParam("userId")String userId){
        return fishService.getWareInventory(userId);
    }

    @RequestMapping("/getCabin")
    @ResponseBody
    public JSONObject getCabinInven(@RequestParam("userId")String userId){
        return fishService.getCabinInventory(userId);
    }
}
