package com.wjh.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjh.cmn.client.DictFeignClient;
import com.wjh.yygh.model.hosp.Hospital;
import com.wjh.yygh.resopitory.HospitalRepository;
import com.wjh.yygh.service.HospitalService;
import com.wjh.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * mongodb
 */
@Service
@SuppressWarnings("all")
public class HospitalServiceImpl implements HospitalService {

    @Resource
    private HospitalRepository hospitalRepository;

    @Autowired
    private DictFeignClient dictFeignClient;


    /**
     * 添加医院
     *
     * @param paraMap
     */
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

    /**
     * 查询医院
     *
     * @param hoscode
     * @return
     */
    @Override
    public Hospital getByHoscode(String hoscode) {
        return hospitalRepository.getHospitalByHoscode(hoscode);
    }

    /**
     * 条件查询带分页
     *
     * @param page
     * @param limit
     * @param hospitalQueryVo
     * @return
     */
    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {

        //Pagerequest
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        //条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();

        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);

        //创建对象
        Example<Hospital> example = Example.of(hospital, exampleMatcher);
        Page<Hospital> pages = hospitalRepository.findAll(example, pageRequest);

        pages.getContent().stream().forEach(item -> {
            this.setHospitalHospType(item);
        });
        return pages;
    }

    /**
     * 获取查询list集合，遍历进行医院等级封装
     *
     * @param hospital
     * @return
     */
    private Hospital setHospitalHospType(Hospital hospital) {
        String hostypeString = dictFeignClient.getName("hostype", hospital.getHostype()); //三甲医院
        String provinceCode = dictFeignClient.getName(hospital.getProvinceCode());
        String cityCode = dictFeignClient.getName(hospital.getCityCode());
        String districtCode = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("fullAddress",provinceCode+cityCode+districtCode);
        hospital.getParam().put("hostypeString", hostypeString);
        return hospital;
    }


}
