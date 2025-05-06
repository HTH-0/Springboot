package com.example.demo.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DataSourceConfigTest {
    @Autowired
    private DataSource dataSource2;

    @Autowired
    private DataSource dataSource3;

    @Test
    public void t1() throws Exception {
        String sql = "INSERT INTO tbl_memo VALUES (?,?,?,now());";
        Connection conn = dataSource2.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, 222);
        pstmt.setString(2, "aaaabbb");
        pstmt.setString(3, "springboot@test.com");

        pstmt.executeUpdate();
    }
    @Test
    public void t2() throws Exception {
        String sql = "INSERT INTO tbl_memo VALUES (?,?,?,now());";
        Connection conn = dataSource3.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql);

        pstmt.setInt(1, 333);
        pstmt.setString(2, "aaaabbb");
        pstmt.setString(3, "springboot@test.com");

        pstmt.executeUpdate();
    }

}