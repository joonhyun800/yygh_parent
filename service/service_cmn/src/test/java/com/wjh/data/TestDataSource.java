package com.wjh.data;

import com.wjh.yygh.cmn.ServiceCmnApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest(classes = ServiceCmnApplication.class)
public class TestDataSource {

    @Autowired
    private DataSource dataSource;


    @Test
    public void getdatasource() throws SQLException {

        System.out.println(dataSource.getConnection());

    }

}
