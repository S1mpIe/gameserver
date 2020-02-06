package com.s1mpie.gameserver.repostiory;


import com.s1mpie.gameserver.model.Hunt;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @author S1mpIe
 */
@Component
public interface HuntMapper {
    Hunt getLastActiveTime(String userId);
    Hunt getLastTime(String userId);
    void startShip(String userId,int huntId,Timestamp beginTime);
    void stopShip(String userId,int huntId);
}