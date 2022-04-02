package com.wjh.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjh.yygh.common.result.Result;
import com.wjh.yygh.model.cmn.Dict;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {

    /**
     * 查询所有字典
     * @param id
     * @return
     */
    List<Dict> findChildData( Long id);

    /**
     * 导出数据字典
     * @param httpServletResponse
     * @return
     */
     void exportData(HttpServletResponse httpServletResponse);


}
