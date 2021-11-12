package com.mercadolivre.control.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolivre.control.dto.StatisticDTO;
import com.mercadolivre.control.entity.Statistic;
import com.mercadolivre.control.service.StatisticService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class StatisticControllerTest {


    @Mock
    private StatisticService statisticService;

    @InjectMocks
    private StatisticController controller;

    private ObjectMapper mapper;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mapper = new ObjectMapper();
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void when_valid_get_then_returns_200() throws Exception {
        //when and then
        mockMvc.perform(get("/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void when_valid_get_then_returns1_404() throws Exception {
        //when and then
        mockMvc.perform(get("/statisticsx")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void when_invalid_post_then_returns_400() throws Exception {
        //when and then
        mockMvc.perform(post("/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void when_valid_post_then_returns_201() throws Exception {
        //given
        StatisticDTO request = new StatisticDTO();
        request.setDate(Calendar.getInstance().getTime());
        request.setDuration(0);
        request.setFail(1);
        request.setLimited(1);
        request.setSuccess(1);
        request.setTotal(4);

        Statistic statistic = new ModelMapper().map(request, Statistic.class);

        //when
        Mockito.when(statisticService.create(any(Statistic.class)))
                .thenReturn(statistic);
        //then
        mockMvc.perform(post("/statistics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(4));
    }
}