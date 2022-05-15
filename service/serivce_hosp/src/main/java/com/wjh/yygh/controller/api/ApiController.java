package com.wjh.yygh.controller.api;

import com.wjh.yygh.common.exception.YyghException;
import com.wjh.yygh.common.result.Result;
import com.wjh.yygh.common.result.ResultCodeEnum;
import com.wjh.yygh.helper.HttpRequestHelper;
import com.wjh.yygh.model.hosp.Department;
import com.wjh.yygh.model.hosp.Hospital;
import com.wjh.yygh.model.hosp.Schedule;
import com.wjh.yygh.service.DeparentmentService;
import com.wjh.yygh.service.HospitalService;
import com.wjh.yygh.service.HospitalSetService;
import com.wjh.yygh.service.ScheduleService;
import com.wjh.yygh.utils.MD5;
import com.wjh.yygh.vo.hosp.DepartmentQueryVo;
import com.wjh.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
@Api(tags = "医院api")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DeparentmentService deparentmentService;

    @Autowired
    private ScheduleService scheduleService;

    /**
     * 查询医院
     * @param request
     * @return
     */
    @ApiOperation("1.查询医院")
    @PostMapping("/hospital/show")
    public Result getHospital(HttpServletRequest request) {

        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String) paramMap.get("hoscode"); //根据hoscode查询signKey 做判断

        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }


    /**
     * 上传医院接口
     * @param request
     * @return
     */
    @ApiOperation("2.添加医院")
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        /**
         * 获取传递过来医院的信息
         */
        Map<String, String[]> requestMap = request.getParameterMap();
        /**
         * 转换
         */
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        /**
         * 验证签名key
         */
        String sign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode"); //根据hoscode查询signKey 做判断

        String SignKeyMd5 = MD5.encrypt(sign);//传过来的key镜像md5加密
        String signKey = hospitalSetService.getSignKey(hoscode);

        //传输过程中 “+” 转成了 “ ” ， 因此要转回来
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);

        if (!signKey.equals(SignKeyMd5)) { //签名不一致
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
        hospitalService.save(paramMap);

        return Result.ok();
    }

    @ApiOperation("2.添加科室")
    @PostMapping("/saveDepartment")
    public Result saveDepartment(HttpServletRequest request){

        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        /**
         * 验证签名key
         */
        String sign = (String) paramMap.get("sign");
        String hoscode = (String) paramMap.get("hoscode"); //根据hoscode查询signKey 做判断

        String SignKeyMd5 = MD5.encrypt(sign);//传过来的key镜像md5加密
        String signKey = hospitalSetService.getSignKey(hoscode);

        if (!SignKeyMd5.equals(signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }
            deparentmentService.save(paramMap);
            return Result.ok();



    }

    /**
     * 查询科室
     * @param request
     * @return
     */
    @ApiOperation("4.查询科室")
    @PostMapping("/department/list")
    public Result getSchedule(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String) paramMap.get("hoscode");

        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        Page<Department> department = deparentmentService.findDepartment(page, limit, departmentQueryVo);
        return Result.ok(department);
    }

    @ApiOperation("5.删除科室")
    @PostMapping("/department/remove")
    public Result departmentRemove(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        deparentmentService.remove(hoscode,depcode);
        return Result.ok();
    }


    /**
     * 上传排版
     * @param request
     * @return
     */
    @ApiOperation("6.增加排班")
    @PostMapping("/saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        scheduleService.save(paramMap);

        return Result.ok();
    }

    /**
     * 查询排班
     * @param request
     * @return
     */
    @ApiOperation("7.查询排班")
    @PostMapping("/schedule/list")
    public Result sehedRuleList(HttpServletRequest request){

        Map<String, String[]> requestParameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        String hoscode = (String)paramMap.get("hoscode");

//        paramMap.put("page", pageNum);
//        paramMap.put("limit", pageSize);
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String) paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);

        Page<Schedule> department = scheduleService.findDepartment(page, limit, scheduleQueryVo);
        return Result.ok(department);

    }

    @ApiOperation("7.删除排班")
    @PostMapping("/schedule/remove")
    public Result removeSchedule(HttpServletRequest request){
        Map<String, String[]> requestParameterMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestParameterMap);

        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

}
