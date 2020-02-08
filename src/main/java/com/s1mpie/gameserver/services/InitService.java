package com.s1mpie.gameserver.services;

import com.alibaba.fastjson.JSONObject;

public interface InitService {
    JSONObject getMap(String userId);
}
