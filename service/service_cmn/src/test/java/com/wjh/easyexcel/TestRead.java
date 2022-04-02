package com.wjh.easyexcel;

import com.alibaba.excel.EasyExcel;

/**
 * 读取 Exce
 */
public class TestRead {

    public static void main(String[] args) {
        String fileName = "D:\\log\\01.xlsx";
        EasyExcel.read(fileName, UserData.class,new ExcelListener()).sheet().doRead();
    }




}
