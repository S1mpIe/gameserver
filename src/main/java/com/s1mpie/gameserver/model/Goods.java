package com.s1mpie.gameserver.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    private int id;
    private String name;
    private int price;
    private int number;
    private String introduce;
    private String target;
    private int ifRepeat;
}
