package com.mercadolivre.control.repository;

import com.mercadolivre.control.entity.Statistic;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface StatisticRepository extends MongoRepository<Statistic, String> {
}
