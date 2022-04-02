package com.wjh.yygh.cmn.controller;

import com.wjh.yygh.cmn.service.DictService;
import com.wjh.yygh.common.result.Result;
import com.wjh.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 导出数据字典
     * @param httpServletResponse
     * @return
     */
    @GetMapping("/export/Data")
    @ApiOperation("导出数据字典")
    public void exportData(HttpServletResponse httpServletResponse){
        dictService.exportData(httpServletResponse);
    }


    /**
     * 查询所有数据字典
     * @param id
     * @return
     */
    @ApiOperation("数据字典查找")
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> childData = dictService.findChildData(id);
        return Result.ok(childData);
    }



}
