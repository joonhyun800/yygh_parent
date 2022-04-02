package com.wjh.yygh.resopitory;

import com.wjh.yygh.model.hosp.Hospital;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.mongodb.repository.MongoRepository;

@Mapper
public interface HospitalRepository extends MongoRepository<Hospital,String> {

    Hospital getHospitalByHoscode(String hoscode);

}
