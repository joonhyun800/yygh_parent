package com.wjh.yygh.service;

import com.wjh.yygh.model.hosp.Department;
import com.wjh.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface DeparentmentService {

    /**
     * 上传科室信息
     * @param paramMap
     */
    void save(Map<String,Object> paramMap);

    Page<Department> findDepartment(int page , int limit, DepartmentQueryVo departmentQueryVo);

    void remove(String hoscode, String depcode);
}
