package com.wjh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-cmn")
@Component
public interface DictFeignClient {

    @RequestMapping(value = "/admin/cmn/dict/getName/{dictCode}/{value}",method = RequestMethod.GET)
    public String getName(@PathVariable("dictCode") String dictCode, @PathVariable("value") String value);

    @RequestMapping(value = "/admin/cmn/dict/getName/{value}",method = RequestMethod.GET)
    public String getName(@PathVariable("value") String value);
}
