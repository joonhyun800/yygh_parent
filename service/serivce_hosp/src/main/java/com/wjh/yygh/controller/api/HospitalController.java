package com.wjh.yygh.controller.api;

import com.wjh.cmn.client.DictFeignClient;
import com.wjh.yygh.common.result.Result;
import com.wjh.yygh.model.hosp.Hospital;
import com.wjh.yygh.service.HospitalService;
import com.wjh.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/hosp/hospital")
@Api(tags = "医院接口")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @ApiOperation(value = "显示所有医院")
    @GetMapping("/list/{page}/{limit}")
    public Result listHosp(@PathVariable("page") Integer page
            , @PathVariable("limit") Integer limit
            , HospitalQueryVo hospitalQueryVo) {

        Page<Hospital> pageModel = hospitalService.selectHospPage(page, limit, hospitalQueryVo);
        List<Hospital> content = pageModel.getContent();
        long totalElements = pageModel.getTotalElements();
        return Result.ok(pageModel);
    }

}
