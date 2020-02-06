package com.s1mpie.gameserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author S1mpIe
 */
@SpringBootApplication
@MapperScan("com.s1mpie.gameserver.repostiory")
public class GameserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameserverApplication.class, args);
    }

}
