package com.s1mpie.gameserver.model;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AccessiblePower {
    String name;
    int number;
    Date birthDay;
}
