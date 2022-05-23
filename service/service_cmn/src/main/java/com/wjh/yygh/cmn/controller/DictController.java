package com.wjh.yygh.cmn.controller;

import com.wjh.yygh.cmn.service.DictService;
import com.wjh.yygh.common.result.Result;
import com.wjh.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(tags = "数据字典接口")
@RestController
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    @Qualifier("dictServiceImpl")
    private DictService dictService;

    /**
     * 导出数据字典
     * @param httpServletResponse
     * @return
     */
    @GetMapping("/exportData")
    @ApiOperation("导出数据字典")
    public void exportData(HttpServletResponse httpServletResponse){
        dictService.exportData(httpServletResponse);
    }

    /**
     * 导入数据字典
     * @param file spring文件传输
     * @return
     */
    @PostMapping("/importData")
    @ApiOperation(value = "导入数据字典")
    public Result importDict(MultipartFile file){
        dictService.importDictData(file);
        return Result.ok();
    }

    /**
     * 查询所有数据字典
     * 根据父节点查找
     * @param id
     * @return
     */
    @ApiOperation("数据字典查找")
    @GetMapping("/findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> childData = dictService.findChildData(id);
        return Result.ok(childData);
    }


    /**
     * 根据dictCode 和 value 查询
     * @param dictCode
     * @param value
     * @return
     */
    @RequestMapping(value = "/getName/{dictCode}/{value}",method = RequestMethod.GET)
    public String getName(@PathVariable("dictCode") String dictCode,@PathVariable("value") String value){
        return dictService.getDictName(dictCode,value);
    }

    /**
     * @param value 根据value查询
     * @return
     */
    @RequestMapping(value = "/getName/{value}",method = RequestMethod.GET)
    public String getName(@PathVariable("value") String value){
        return dictService.getDictName("",value);
    }


    /**
     * 根据dictCode 获取子节点
     * @param dictCode
     * @return
     */
    @ApiOperation(value = "根据dictCode查询下级节点")
    @RequestMapping(value = "/findByDictCode/{dictCode}",method = RequestMethod.GET)
    public Result findByDictCode(@PathVariable("dictCode") String dictCode){
        List<Dict> byDictCode = dictService.findByDictCode(dictCode);
        return Result.ok(byDictCode);
    }



}
