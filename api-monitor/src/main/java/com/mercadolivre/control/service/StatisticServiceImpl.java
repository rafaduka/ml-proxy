package com.mercadolivre.control.service;

import com.mercadolivre.control.entity.Statistic;
import com.mercadolivre.control.repository.StatisticRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatisticServiceImpl implements StatisticService {

    private final StatisticRepository repository;

    public StatisticServiceImpl(StatisticRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Statistic> findAll() {
        return repository.findAll();
    }

    @Override
    public Statistic create(Statistic statistic) {
        return repository.save(statistic);
    }
}
