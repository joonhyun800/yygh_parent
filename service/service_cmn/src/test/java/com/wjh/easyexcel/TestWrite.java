package com.wjh.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.LinkedList;

public class TestWrite {

    public static void main(String[] args) {

        String fileName = "D:\\log\\01.xlsx";

        LinkedList<UserData> userDataLinkedList = new LinkedList<UserData>();

        for (int i = 0; i < 10; i++) {
            UserData userData = new UserData();
            userData.setUid(i);
            userData.setUsername("lusy"+i);
            userDataLinkedList.add(userData);
        }

        EasyExcel.write(fileName,UserData.class).sheet("用户信息").doWrite(userDataLinkedList);

    }
}
