package com.mercadolivre.proxy.schedule;

import com.mercadolivre.proxy.client.StatisticServiceClient;
import com.mercadolivre.proxy.dto.StatisticDTO;
import com.mercadolivre.proxy.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    Logger logger = LoggerFactory.getLogger(StatisticScheduler.class);
    private final StatisticServiceClient statisticClient;
    private final StatisticService statisticService;

    public StatisticScheduler(StatisticServiceClient statisticClient,
                              StatisticService statisticService) {
        this.statisticClient = statisticClient;
        this.statisticService = statisticService;
    }

    /**
     * 60000 = 1 min
     * 3600000 = 60 min
     */
    @Scheduled(fixedDelay = 60000)
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
        logger.info("Requested to create statistics");
        statisticService.reset();
        logger.info("Request to reset database");
    }
}
