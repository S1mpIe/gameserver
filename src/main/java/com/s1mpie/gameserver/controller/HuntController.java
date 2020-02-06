package com.s1mpie.gameserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.services.HuntService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author S1mpIe
 */
@Controller
@RequestMapping("/hunt")
public class HuntController {
    @Autowired
    private HuntService huntService;
    @RequestMapping("/time")
    @ResponseBody
    public JSONObject getShipTime(@Param("userId") String userId){
        return huntService.getLastBeginTime(userId);
    }

    @RequestMapping("/start")
    @ResponseBody
    public JSONObject startShip(@Param("userId") String userId){
        return huntService.startShip(userId);
    }

    @RequestMapping("/stop")
    @ResponseBody
    public JSONObject stopShip(@Param("userId") String userId){
        return huntService.stopShip(userId);
    }
}
