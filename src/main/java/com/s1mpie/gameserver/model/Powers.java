package com.s1mpie.gameserver.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Powers {
    private String userId;
    private int current;
    private int max;
}
