package com.wjh.yygh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.wjh.yygh.model.hosp.Department;
import com.wjh.yygh.model.hosp.Schedule;
import com.wjh.yygh.resopitory.ScheduleRepository;
import com.wjh.yygh.service.ScheduleService;
import com.wjh.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;


    @Override
    public void save(Map<String, Object> paramMap) {
        String s = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(s, Schedule.class);

        Schedule scheduleExist = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(), schedule.getHosScheduleId());

        if (StringUtils.isEmpty(scheduleExist)){
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }else {
            scheduleExist.setStatus(1);
            scheduleExist.setCreateTime(new Date());
            scheduleRepository.save(scheduleExist);
        }

    }

    /**
     * 查询排班接口接口
     * @param page
     * @param limit
     * @param scheduleQueryVo
     * @return
     */
    @Override
    public Page<Schedule> findDepartment(int page, int limit, ScheduleQueryVo scheduleQueryVo) {

        PageRequest pageRequest = PageRequest.of(page-1, limit);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo,schedule);

        Example<Schedule> of = Example.of(schedule, exampleMatcher);

        Page<Schedule> all = scheduleRepository.findAll(of, pageRequest);

        return all;

    }

    @Override
    public void remove(String hoscode, String hosScheduleId) {
        Schedule scheduleByHoscodeAndhAndHosScheduleId = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);

        if (!StringUtils.isEmpty(scheduleByHoscodeAndhAndHosScheduleId)){
            scheduleRepository.deleteById(scheduleByHoscodeAndhAndHosScheduleId.getId());
        }


    }
}
