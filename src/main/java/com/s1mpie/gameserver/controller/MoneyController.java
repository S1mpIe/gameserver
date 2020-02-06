package com.s1mpie.gameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/money")
public class MoneyController {
    @Autowired
    private MoneyService moneyService;
    @RequestMapping("/getCurrent")
    @ResponseBody
    public JSONObject getCurrentMoney(@RequestParam("userId")String userId){
        return moneyService.getCurrentMoney(userId);
    }
}
