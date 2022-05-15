package com.wjh.yygh.service;

import com.wjh.yygh.model.hosp.Schedule;
import com.wjh.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    void save(Map<String, Object> paramMap);

    Page<Schedule> findDepartment(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String hoscode, String hosScheduleId);
}
