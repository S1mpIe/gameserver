package com.s1mpie.gameserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaterPiece {
    private int x;
    private int y;
    private int cate;
    private String name;
    private int powerCost;
    private int buff;
    private int ifGet;
}
