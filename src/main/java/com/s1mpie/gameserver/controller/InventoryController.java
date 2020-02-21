package com.s1mpie.gameserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.FishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/inventory")
public class InventoryController {
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

    @RequestMapping("/storage")
    @ResponseBody
    public JSONObject cabinToInven(@RequestParam("userId")String userId,@RequestParam(value = "fishId",required = false)Integer fishId){
        if (fishId == null){
           return fishService.moveCabinToWare(userId);
        }else {
            return fishService.moveCabinToWare(userId,fishId);
        }
    }

    @RequestMapping("/sell")
    @ResponseBody
    public JSONObject wareSell(@RequestParam("userId")String userId,@RequestParam("fishId")int fishId,@RequestParam("number")int number){
        return fishService.sellWareInventory(userId, fishId, number);
    }
}
