package com.wjh.yygh.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjh.yygh.common.exception.YyghException;
import com.wjh.yygh.common.result.Result;
import com.wjh.yygh.model.hosp.HospitalSet;
import com.wjh.yygh.service.HospitalSetService;
import com.wjh.yygh.utils.MD5;
import com.wjh.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Api(tags = {"后台医院设置"})
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {


    //注入service
    @Autowired
    private HospitalSetService hospitalSetService;

    //1 查询医院设置表所有信息
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("/findAll")
    public Result findAllHospitalSet() {
        //调用service的方法
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    //2 逻辑删除医院设置
    @ApiOperation(value = "逻辑删除医院设置")
    @DeleteMapping("/{id}")
    public Result removeHospSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //3 条件查询带分页
    @ApiOperation(value = "3 条件查询带分页")
    @PostMapping("/findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody
                                          (required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();//医院名称
        String hoscode = hospitalSetQueryVo.getHoscode();//医院编号
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hospitalSetQueryVo.getHosname());
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
        }
        //调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
        //返回结果
        return Result.ok(pageHospitalSet);
    }

    //4 添加医院设置
    @ApiOperation(value = "4 添加医院设置")
    @PostMapping("/saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态 1 使用 0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        String encrypt = MD5.encrypt(System.currentTimeMillis() + "" + new Random().nextInt(1000));
        hospitalSet.setSignKey(encrypt);
        //调用service

        boolean save = hospitalSetService.save(hospitalSet);
        if (save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //5 根据id获取医院设置
    @ApiOperation(value = "5 根据id获取医院设置")
    @GetMapping("/getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {

        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //6 修改医院设置
    @ApiOperation(value = "6 修改医院设置")
    @PostMapping("/updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    //7 批量删除医院设置
    @ApiOperation(value = "7 批量删除医院设置")
    @DeleteMapping("/batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

//    8.医院设置锁定和解锁
    @ApiOperation(value = "8.医院设置锁定和解锁")
    @PutMapping("/lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable("id") Long id
                ,@PathVariable("status") Integer status){

        //根据id查询医院设置信息
        HospitalSet byId = hospitalSetService.getById(id);
//        设置状态信息
        byId.setStatus(status);
//        调用方法
        hospitalSetService.updateById(byId);
        return Result.ok();
    }

    //9.发送签名秘钥
    @ApiOperation(value = "9.发送签名秘钥")
    @PutMapping("/sendKey/{id}")
    public Result sendKey(@PathVariable("id") Long id){
        HospitalSet byId = hospitalSetService.getById(id);
        String signKey = byId.getSignKey();
        String hoscode = byId.getHoscode();
        //TODO 发送短信
        return Result.ok();

    }




}
