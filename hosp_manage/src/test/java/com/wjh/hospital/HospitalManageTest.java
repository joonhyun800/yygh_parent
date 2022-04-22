package com.wjh.hospital;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Slf4j
public class HospitalManageTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testData(){

        try {
            Connection connection = dataSource.getConnection();
            log.info("连接：{}",connection);
        } catch (SQLException e) {
            log.info("连接错误");
            e.printStackTrace();
        }
    }

}
