package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Coordinate;
import com.s1mpie.gameserver.model.WaterPiece;
import org.springframework.stereotype.Component;

@Component
public interface CoordinateMapper {
    Coordinate queryCurrent(String userId);
    WaterPiece[] queryWholeMap(String userId);
    WaterPiece queryPiece(String userId,int x,int y);
    WaterPiece queryCurrentPiece(String userId);
    int updateCurrent(String userId,int x,int y);
}
