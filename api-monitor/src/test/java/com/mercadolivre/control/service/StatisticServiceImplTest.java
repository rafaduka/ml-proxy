package com.mercadolivre.control.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.mercadolivre.control.entity.Statistic;
import com.mercadolivre.control.repository.StatisticRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;

@RunWith(MockitoJUnitRunner.class)
public class StatisticServiceImplTest {

    @Mock
    private StatisticRepository repository;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private StatisticServiceImpl service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll() {

        List<Statistic> expected = Lists.newArrayList();
        expected.add(new Statistic());

        when(repository.findAll()).thenReturn(expected);

        List<Statistic> actual = service.findAll();

        assertThat(actual, is(expected));
    }

    @Test
    public void create() {
        //given
        Statistic statistic = new Statistic();
        statistic.setId("1");

        //when
        service.create(statistic);

        //then
        verify(repository, times(1)).save(any(Statistic.class));
    }

    @Test
    public void deleteAll() {
        service.deleteAll();
        verify(mongoTemplate, times(1)).dropCollection(Statistic.class);
    }
}