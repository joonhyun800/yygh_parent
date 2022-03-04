package com.wjh.yygh;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
@Slf4j
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void datasourcetest() throws SQLException {
        System.out.println(dataSource.getConnection());
    }


}
