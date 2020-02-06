package com.s1mpie.gameserver.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Hunt {
    private String userId;
    private int huntId;
    private Timestamp beginTime;
    private String status;
}
