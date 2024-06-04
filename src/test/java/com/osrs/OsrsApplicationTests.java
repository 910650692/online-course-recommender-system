package com.osrs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootTest
class OsrsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testConnection(){
        String url = "jdbc:mysql://localhost:3306/osrs?useSSL=true&useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC";
        String username = "root";
        String password = "123456";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("连接成功！");
            connection.close();
        } catch (SQLException e) {
            System.out.println("连接失败！");
            e.printStackTrace();
        }
    }
}