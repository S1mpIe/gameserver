package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Goods;
import org.springframework.stereotype.Component;

@Component
public interface PropMapper {
    Goods[] queryAllProps(String userId);
    int updatePropNumber(String userId,int porpId);

}
