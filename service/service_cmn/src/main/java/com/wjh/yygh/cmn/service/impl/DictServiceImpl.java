package com.wjh.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjh.yygh.cmn.mapper.DictMapper;
import com.wjh.yygh.cmn.service.DictService;
import com.wjh.yygh.model.cmn.Dict;
import com.wjh.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * @param id
     * @return
     */
    @Override
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            Long dictId = dict.getId();
            boolean children = this.isChildren(id);
            dict.setHasChildren(children);
        }
        return dicts;
    }

    /**
     * 导出所有数据字典
     * @param response
     * Content-dixposition 以下载方式
     * response.setContentType 设置下载信息
     *
     */
    @Override
    public void exportData(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName= "dict";
        response.setHeader("Content-dixposition","attachment;filename"+fileName+".xlsx");

        List<Dict> dictList = baseMapper.selectList(null);

        List<DictEeVo> dictEeVoLinkedList = new ArrayList<>();
        for (Dict dict : dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            /**
             * dictEeVo.setId(dict.getId())
             */
            BeanUtils.copyProperties(dictList,dictEeVoLinkedList);
        }
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(dictEeVoLinkedList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否有子节点
     * @param id
     * @return
     */
    private boolean isChildren(Long id){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count>0;
    }

}
