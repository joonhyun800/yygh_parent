package com.wjh.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjh.yygh.model.hosp.Department;
import com.wjh.yygh.resopitory.DeparentmentRespository;
import com.wjh.yygh.service.DeparentmentService;
import com.wjh.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

import java.util.Map;

@Component
public class DeparentmentServiceImpl implements DeparentmentService {

    @Autowired
    private DeparentmentRespository deparentmentRespository;


    /**
     * 上传科室信息
     * @param paramMap
     */
    @Override
    public void save(Map<String,Object> paramMap) {

        String s1 = JSONObject.toJSONString(paramMap);
        Department department = JSONObject.parseObject(s1, Department.class);

        Department departmentsExist = deparentmentRespository.getDepartmentsByHoscodeAndDepcode(department.getHoscode(), department.getDepcode());

        if (!StringUtils.isEmpty(departmentsExist)){
            departmentsExist.setUpdateTime(new Date());
            departmentsExist.setIsDeleted(0);
            deparentmentRespository.save(departmentsExist);
        }else {
            department.setCreateTime(new Date());
            department.setUpdateTime(new Date());
            department.setIsDeleted(0);
            deparentmentRespository.save(department);
        }

    }

    /**
     * 查询科室
     * @param page
     * @param limit
     * @param departmentQueryVo
     * @return
     */
    @Override
    public Page<Department> findDepartment(int page, int limit,DepartmentQueryVo departmentQueryVo) {


        Pageable pageable = PageRequest.of(page-1,limit);

        ExampleMatcher matching = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();

        Department department = new Department();
        BeanUtils.copyProperties(departmentQueryVo,department);

        Example<Department> of = Example.of(department, matching);

        Page<Department> all = deparentmentRespository.findAll(of, pageable);
        return all;
    }

    /**
     * 删除科室
     * @param hoscode
     * @param depcode
     */
    @Override
    public void remove(String hoscode, String depcode) {

        Department departmentsByHoscodeAndDepcode = deparentmentRespository.getDepartmentsByHoscodeAndDepcode(hoscode, depcode);
        if (!StringUtils.isEmpty(departmentsByHoscodeAndDepcode)){
            deparentmentRespository.deleteById(departmentsByHoscodeAndDepcode.getId());
        }
    }

    

}
