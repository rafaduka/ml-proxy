package com.mercadolivre.control.service;

import com.mercadolivre.control.entity.Statistic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface StatisticService {

    /**
     * Find all statistics.
     *
     * @return statistics.
     */
    List<Statistic> findAll();

    /**
     * Create a new statistic.
     *
     * @param statistic to be created.
     * @return the created statistic.
     */
    Statistic create(Statistic statistic);
}
