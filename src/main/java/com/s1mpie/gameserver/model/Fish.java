package com.s1mpie.gameserver.model;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Fish {
    private int id;
    private String name;
    private double price;
    private double probability;
    private int number;
}
