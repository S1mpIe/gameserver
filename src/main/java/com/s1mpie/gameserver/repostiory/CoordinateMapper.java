package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Coordinate;
import org.springframework.stereotype.Component;

@Component
public interface CoordinateMapper {
    Coordinate queryCurrent(String userId);
}
