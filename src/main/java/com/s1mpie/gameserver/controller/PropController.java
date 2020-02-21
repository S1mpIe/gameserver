package com.s1mpie.gameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.PropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/prop")
public class PropController {
    @Autowired
    private PropService propService;

    @RequestMapping("/shop")
    @ResponseBody
    public JSONObject shopProps(@RequestParam("userId")String userId){
        return propService.getAllGoods(userId);
    }
    @RequestMapping("/buy")
    @ResponseBody
    public JSONObject buyProps(@RequestParam("userId")String userId,@RequestParam("propId")int propId,@RequestParam("number")int number){
        return propService.buyProp(userId, propId, number);
    }
    @RequestMapping("/use")
    @ResponseBody
    public JSONObject useProps(@RequestParam("userId")String userId,@RequestParam("propId")int propId,@RequestParam("number")int number){
        return propService.useProp(userId, propId, number);
    }
    @RequestMapping("/buff")
    @ResponseBody
    public JSONObject getAllBuff(@RequestParam("userId")String userId){
        return propService.getAllBuff(userId);
    }
}
