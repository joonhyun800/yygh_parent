package com.wjh.yygh.service;

import com.wjh.yygh.model.hosp.Hospital;

import java.util.Map;

public interface HospitalService {

    /**
     * 上传医院接口方法
     * @param paramMap
     */
    void save(Map<String ,Object> paramMap);

    Hospital getByHoscode(String hoscode);

}
