package com.wjh.yygh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjh.yygh.mapper.HospitalSetMapper;
import com.wjh.yygh.model.hosp.HospitalSet;
import com.wjh.yygh.service.HospitalSetService;
import com.wjh.yygh.utils.MD5;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    @Override
    public Integer saveHospitalSet(HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        String encrypt = MD5.encrypt(System.currentTimeMillis() + "" + new Random().nextInt(1000));
        hospitalSet.setSignKey(encrypt);
        return baseMapper.insert(hospitalSet);
    }

    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(queryWrapper);
        return hospitalSet.getSignKey();
    }
}
