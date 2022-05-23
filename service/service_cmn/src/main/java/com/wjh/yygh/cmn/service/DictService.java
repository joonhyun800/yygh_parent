package com.wjh.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjh.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

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

     void importDictData(MultipartFile file);

    String getDictName(String dictCode, String value);

    List<Dict> findByDictCode(String dictCode);
}
