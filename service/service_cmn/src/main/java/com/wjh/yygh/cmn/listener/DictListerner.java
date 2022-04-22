package com.wjh.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.wjh.yygh.cmn.mapper.DictMapper;
import com.wjh.yygh.model.cmn.Dict;
import com.wjh.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListerner extends AnalysisEventListener<DictEeVo> {

    /**
     * 构造方法注入
     */
    private DictMapper dictMapper;
    public DictListerner(DictMapper dictMapper){
        this.dictMapper=dictMapper;
    }

    /**
     * 会逐步读取
     * @param dictEeVo
     * @param analysisContext
     */
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        /**
         * dict 赋值到 dictEevo
         */
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
