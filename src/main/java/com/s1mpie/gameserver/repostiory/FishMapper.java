package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Fish;
import org.springframework.stereotype.Component;

@Component
public interface FishMapper {
    Fish[] queryAllCate();
    Fish[] queryWareHouse(String userId);
    Fish queryWareSimple(String userId, int fishId);
    int updateWareHouse(String userId,int fishId,int number);
    int insertFish(String userId,int fishId,int number);
}
