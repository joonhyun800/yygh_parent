package com.wjh.yygh.controller;

import com.wjh.yygh.common.result.Result;
import com.wjh.yygh.helper.HttpRequestHelper;
import com.wjh.yygh.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class apiApiController {

    @Autowired
    private HospitalService hospitalService;

    /**
     * 上传医院接口
     * @param request
     * @return
     */
    @PostMapping("/saveHospital")
    public Result saveHosp(HttpServletRequest request){
        /**
         * 获取传递过来医院的信息
         */
        Map<String, String[]> parameterMap = request.getParameterMap();
        /**
         * 转换
         */
        Map<String, Object> stringObjectMap = HttpRequestHelper.switchMap(parameterMap);
        hospitalService.save(stringObjectMap);
        return Result.ok();
    }


}
