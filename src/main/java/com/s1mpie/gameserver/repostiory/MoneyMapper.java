package com.s1mpie.gameserver.repostiory;

import com.s1mpie.gameserver.model.Money;
import org.springframework.stereotype.Component;

@Component
public interface MoneyMapper {
    Money queryCurrent(String userId);
}
