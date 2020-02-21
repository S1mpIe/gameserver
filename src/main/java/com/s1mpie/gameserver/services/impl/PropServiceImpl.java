package com.s1mpie.gameserver.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.s1mpie.gameserver.model.Buff;
import com.s1mpie.gameserver.model.Cabin;
import com.s1mpie.gameserver.model.Goods;
import com.s1mpie.gameserver.model.Money;
import com.s1mpie.gameserver.repostiory.MoneyMapper;
import com.s1mpie.gameserver.repostiory.PowerMapper;
import com.s1mpie.gameserver.repostiory.PropMapper;
import com.s1mpie.gameserver.services.PowerService;
import com.s1mpie.gameserver.services.PropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PropServiceImpl implements PropService {
    @Autowired
    private PropMapper propMapper;
    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    private PowerService powerService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public JSONObject getAllGoods(String userId) {
        Goods[] goods = propMapper.queryShopProps(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("propArr",goods);
        return jsonObject;
    }

    @Override
    public JSONObject getBagInventory(String userId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("proArr",propMapper.queryBagProps(userId));
        return jsonObject;
    }

    @Override
    public JSONObject buyProp(String userId, int propId,int number) {
        Money money = moneyMapper.queryCurrent(userId);
        Goods goods = propMapper.queryProp(propId);
        JSONObject jsonObject = new JSONObject();
        if(goods.getIfRepeat() == 0 && number > 1){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","当前物品购买数量不能超过1");
        } else if(money.getCurrent() >= number * goods.getPrice()){
            moneyMapper.updateCurrent(userId, money.getCurrent() - number * goods.getPrice());
            Goods bagProp = propMapper.queryBagSimpleProps(userId, propId);
            if(bagProp == null){
                propMapper.insertBagProp(userId, propId, number);
            }else {
                propMapper.updatePropNumber(userId,propId,bagProp.getNumber() + number);
            }
            jsonObject.put("status","success");
        }else {
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","您的余额不足");
        }
        return jsonObject;
    }

    @Override
    public JSONObject useProp(String userId, int propId,int number) {
        Goods bagGoods = propMapper.queryBagSimpleProps(userId, propId);
        JSONObject jsonObject = new JSONObject();
        if(bagGoods.getNumber() < number){
            jsonObject.put("status","failed");
            jsonObject.put("errmsg","你的库存不足");
        }else {
            jsonObject.put("status","success");
            if(bagGoods.getIfRepeat() == 1 || bagGoods.getNumber() != 1){
                propMapper.updatePropNumber(userId, propId,bagGoods.getNumber() - number);
            }else {
                propMapper.updatePropNumber(userId, propId,-1);
            }
            if(bagGoods.getName().equals("发动机")){
                jsonObject = powerService.increaseMaxPower(userId,1 * number);
            }else if (bagGoods.getName().equals("货舱")){
                Cabin cabin = propMapper.queryCabin(userId);
                propMapper.updateCabin(userId,cabin.getMaxCabin() + 1 * number);
            }else if (bagGoods.getName().equals("许可证")){
                Buff buff = (Buff) redisTemplate.opsForHash().get("Buff", userId);
                if(buff == null){
                    buff = new Buff("money",0.2,"gain","许可证",12 * number,new Date(System.currentTimeMillis()));
                }else {
                    Date date = new Date(System.currentTimeMillis());
                    if (buff.getBirthTime().getHours() - date.getHours() < buff.getLastTime()){
                        buff.setLastTime(buff.getLastTime() + 12 * number);
                    }else {
                        buff.setLastTime(12 * number);
                        buff.setBirthTime(date);
                    }
                }
                redisTemplate.opsForHash().put("Buff",userId,buff);
            }else if (bagGoods.getName().equals("电池")){
                jsonObject = powerService.increaseCurrentPower(userId,2 * number);
            }
        }
        return jsonObject;
    }

    @Override
    public JSONObject getAllBuff(String userId) {
        Buff buff = (Buff) redisTemplate.opsForHash().get("Buff", userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("buff",buff);
        return jsonObject;
    }
}
