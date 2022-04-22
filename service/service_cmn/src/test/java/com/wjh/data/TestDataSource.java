package com.wjh.data;

import com.wjh.yygh.cmn.ServiceCmnApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest(classes = ServiceCmnApplication.class)
@Slf4j
public class TestDataSource {

    @Autowired
    private DataSource dataSource;


    @Test
    public void getdatasource() throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            log.info("连接结果：{}",connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
