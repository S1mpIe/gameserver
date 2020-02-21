package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Cabin;
import com.s1mpie.gameserver.model.Goods;
import org.springframework.stereotype.Component;

@Component
public interface PropMapper {
    Goods[] queryShopProps(String userId);
    Goods[] queryBagProps(String userId);
    Goods queryBagSimpleProps(String userId,int propId);
    Goods queryProp(int propId);
    int updatePropNumber(String userId,int propId,int number);
    int insertBagProp(String userId,int propId,int number);
    Cabin queryCabin(String userId);
    int updateCabin(String userId,int number);
}
