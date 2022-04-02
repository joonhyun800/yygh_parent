package com.wjh.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjh.yygh.model.hosp.Hospital;
import com.wjh.yygh.resopitory.HospitalRepository;
import com.wjh.yygh.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
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
//       把map转换成字符串
        String mapString = JSONObject.toJSONString(paraMap);
//        字符串转换成对象
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        /**
         * 判断是否存在数据
         */
        String hoscode = hospital.getHoscode();
         Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        if (!StringUtils.isEmpty(hospitalExist)){
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(hospitalExist.getUpdateTime());
            hospital.setIsDeleted(hospitalExist.getIsDeleted());
            hospitalRepository.save(hospital);
        }else {
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }

    }
}
