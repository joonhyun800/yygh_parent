package com.wjh.yygh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjh.yygh.model.hosp.HospitalSet;

public interface HospitalSetService extends IService<HospitalSet> {

    public Integer saveHospitalSet(HospitalSet hospitalSet);

    //获取key进行签名校验
    String getSignKey(String hoscode);
}
