package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Powers;
import org.springframework.stereotype.Component;

@Component
public interface PowerMapper {
    Powers queryCurrent(String userId);
}
