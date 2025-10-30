package com.sam.sam_biz.demo;

import com.sam.sap_commons.exceptions.ApiException;
import com.sam.sap_commons.exchange.ApiMsg;
import com.sam.sap_commons.utils.RedisHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/api/demo")
public class DemoController {
    /**
     *
     * @return ApiMsg<?>
     */
    @ResponseBody
    @RequestMapping("/log/{apiParam}")
    public ApiMsg<?> getWaitingCreatingPOSOListS4(@PathVariable String apiParam) throws ApiException {
        log.error("log {} {}", "hello", apiParam);
        log.error("log {} {}", "World", apiParam);
        return ApiMsg.success(RedisHelper.newKey("log"));
    }
}
