package com.wjh.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjh.yygh.cmn.listener.DictListerner;
import com.wjh.yygh.cmn.mapper.DictMapper;
import com.wjh.yygh.cmn.service.DictService;
import com.wjh.yygh.model.cmn.Dict;
import com.wjh.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    /**
     * 查询数据字典
     * @param id
     * @return
     * @Cacheable 生成缓存
     */
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        List<Dict> dicts = baseMapper.selectList(queryWrapper);
        for (Dict dict : dicts) {
            Long dictId = dict.getId();
            boolean children = this.isChildren(dictId);
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
        /**
         * 与我对接的前端使用的是xlsx格式接收，所以后端应该设置的是：
         * response.setContentType(“application/vnd.openxmlformats-officedocument.spreadsheetml.sheet”)
         *
         * 相反，如果使用xls格式接受，后端则要设置：
         * response.setContentType(“application/vnd.ms-excel”)
         */
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = null;
        try {
            fileName = URLEncoder.encode("数据字典.xlsx", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            fileName="dict";
            e.printStackTrace();
        }
        response.setHeader("Content-disposition","attachment;filename"+fileName+".xlsx");


        List<Dict> dictList = baseMapper.selectList(null);

        List<DictEeVo> dictEeVoLinkedList = new ArrayList<>();

        for (Dict dict : dictList) {
            DictEeVo dictEeVo = new DictEeVo();
            /**
             * 对象拷贝 Dict => DictEeVo
             * dictEeVo.setId(dict.getId())
             */
            BeanUtils.copyProperties(dict,dictEeVo);
            dictEeVoLinkedList.add(dictEeVo);
        }
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典")
                    .doWrite(dictEeVoLinkedList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 导入数据字典
     * @param file
     * allEntries = true: 方法调用后清空所有缓存
     */
    @Override
    @CacheEvict(value = "dict",allEntries = true)
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class, new DictListerner(baseMapper))
                    .sheet().doRead();
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
