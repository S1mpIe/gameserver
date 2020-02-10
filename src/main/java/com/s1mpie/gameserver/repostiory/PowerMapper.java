package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Powers;
import org.springframework.stereotype.Component;

@Component
public interface PowerMapper {
    Powers queryCurrent(String userId);
    int updateCurrent(String userId,int power);
    int updateMax(String userId,int power);
}
