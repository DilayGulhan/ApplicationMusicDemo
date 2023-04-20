package com.Dilay.AppMusicDemo;

import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@RunWith(SpringRunner.class)
class GguidesApiApplicationTests {

    public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) (new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("db")
            .withUsername("postgres")
            .withPassword("2446"))
            .withReuse(true);

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();

        System.setProperty("jdbc:postgresql://localhost:5432/MusicAppDemo", postgreSQLContainer.getJdbcUrl());
        System.setProperty("postgres", postgreSQLContainer.getUsername());
        System.setProperty("2446", postgreSQLContainer.getPassword());
    }
    @Test
    void contextLoads() {

    }


}