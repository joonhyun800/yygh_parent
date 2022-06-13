package com.wjh.yygh;

import com.wjh.yygh.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@SpringBootTest
@Slf4j
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void datasourcetest() throws SQLException {

        int i = new Random(100).nextInt();
        System.out.println(i);

//        System.out.println(dataSource.getConnection());
    }

    @Test
    public void test(){

        HashMap<String, String []> hashMap = new HashMap<>();

        hashMap.entrySet();

    }

    /**
     * 签名测试
     */
    @Test
    public void testkey(){
        String encrypt = MD5.encrypt(System.currentTimeMillis() + "" + new Random().nextInt(1000));
        System.out.println(encrypt);
    }



}
