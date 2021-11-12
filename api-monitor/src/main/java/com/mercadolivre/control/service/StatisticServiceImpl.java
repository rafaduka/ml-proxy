package com.mercadolivre.control.service;

import com.mercadolivre.control.entity.Statistic;
import com.mercadolivre.control.repository.StatisticRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatisticServiceImpl implements StatisticService {

    private StatisticRepository repository;
    private MongoTemplate mongoTemplate;

    public StatisticServiceImpl(StatisticRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Statistic> findAll() {
        List<Statistic> statistics = repository.findAll();
        return statistics;
    }

    @Override
    public Statistic create(Statistic statistic) {
        return repository.save(statistic);
    }

    @Override
    public void deleteAll() {
        mongoTemplate.dropCollection(Statistic.class);
    }

}
