package com.mercadolivre.proxy.client;

import com.mercadolivre.proxy.dto.StatisticDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "monitor-service", path = "/statistics")
public interface StatisticServiceClient {

    @RequestMapping(method = RequestMethod.POST)
    void create(@RequestBody StatisticDTO statisticDTO);
}
