package com.s1mpie.gameserver.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Buff {
    private String target;
    private double number;
    private String cate;
    private String name;
    private int lastTime;
    private Date birthTime;
}
