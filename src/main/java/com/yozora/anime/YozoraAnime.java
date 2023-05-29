package com.yozora.anime;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@MapperScan("com.yozora.anime.dao")

public class YozoraAnime {

    public static void main(String[] args) {
        SpringApplication.run(YozoraAnime.class);
    }
}

