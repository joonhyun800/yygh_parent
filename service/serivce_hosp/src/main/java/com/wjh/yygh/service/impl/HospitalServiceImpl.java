package com.wjh.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjh.yygh.model.hosp.Hospital;
import com.wjh.yygh.resopitory.HospitalRepository;
import com.wjh.yygh.service.HospitalService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Resource
    private HospitalRepository hospitalRepository;


    @Override
    public void save(Map<String, Object> paraMap) {
//       把map转换成字符串 常用
        String mapString = JSONObject.toJSONString(paraMap);
//       字符串转换成对象
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        /**
         * 判断是否存在数据
         */
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        /**
         * 如果为空就添加，不为空就修改
         */
        if (StringUtils.isEmpty(hospitalExist)) { //添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else { //修改
            hospitalExist.setStatus(hospitalExist.getStatus());
//            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospitalExist.setUpdateTime(hospitalExist.getUpdateTime());
            hospitalExist.setIsDeleted(hospitalExist.getIsDeleted());
            hospitalRepository.save(hospitalExist);
        }

    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }
}
