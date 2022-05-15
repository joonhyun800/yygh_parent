package com.wjh.yygh.resopitory;

import com.wjh.yygh.model.hosp.Department;
import com.wjh.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface DeparentmentRespository extends MongoRepository<Department,String> {

    Department getDepartmentsByHoscodeAndDepcode(String hoscode, String depcode);



}
