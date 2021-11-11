package com.mercadolivre.proxy.schedule;

import com.mercadolivre.proxy.client.StatisticServiceClient;
import com.mercadolivre.proxy.dto.StatisticDTO;
import com.mercadolivre.proxy.service.StatisticService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@EnableAsync
@Configuration
@EnableScheduling
public class StatisticScheduler {

    private final StatisticServiceClient statisticClient;
    private final StatisticService statisticService;

    public StatisticScheduler(StatisticServiceClient statisticClient,
                              StatisticService statisticService) {
        this.statisticClient = statisticClient;
        this.statisticService = statisticService;
    }

    @Scheduled(fixedDelay = 60000)
//    @Scheduled(fixedDelay = 3600000)
    public void sendDailyStatistics() {
        final StatisticDTO statistic = new StatisticDTO(
                statisticService.getTotal(),
                statisticService.getSuccess(),
                statisticService.getError(),
                statisticService.getRateLimit(),
                statisticService.getDuration(),
                new Date()
        );
        statisticClient.create(statistic);
        statisticService.reset();
    }
}
