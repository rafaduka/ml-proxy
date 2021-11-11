package com.mercadolivre.proxy.service;

import com.mercadolivre.proxy.entity.Request;
import com.mercadolivre.proxy.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class StatisticServiceImpl implements StatisticService {

    private final RequestRepository requestRepository;

    @Autowired
    public StatisticServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public long getTotal() {
        return getRequests().count();
    }

    @Override
    public long getSuccess() {
        return getRequests()
                .filter(request -> request.getStatus() >= 200 &&
                        request.getStatus() < 300)
                .count();
    }

    @Override
    public long getError() {
        return getRequests()
                .filter(request -> request.getStatus() >= 400 &&
                        request.getStatus() < 500 &&
                        request.getStatus() != 429)
                .count();
    }

    @Override
    public long getRateLimit() {
        return getRequests()
                .filter(request -> request.getStatus() == 429)
                .count();
    }

    @Override
    public long getDuration() {
        return (long) getRequests()
                .mapToLong(Request::getDuration)
                .average()
                .orElse(0);
    }

    @Override
    public void reset() {
        requestRepository.deleteAll();
    }

    private Stream<Request> getRequests() {
        return StreamSupport.stream(requestRepository.findAll().spliterator(), false);
    }
}
