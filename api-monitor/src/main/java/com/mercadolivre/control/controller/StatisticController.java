package com.mercadolivre.control.controller;

import com.mercadolivre.control.dto.StatisticDTO;
import com.mercadolivre.control.entity.Statistic;
import com.mercadolivre.control.service.StatisticService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:4200"})
public class StatisticController {

    private StatisticService statisticService;
    private ModelMapper modelMapper;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
        this.modelMapper = new ModelMapper();
    }

    @GetMapping
    public ResponseEntity<List<StatisticDTO>> findAll() {

        final List<Statistic> statistics = statisticService.findAll();

        final List<StatisticDTO> response = statistics.stream()
                .map(statistic -> modelMapper.map(statistic, StatisticDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<StatisticDTO> create(@RequestBody @Valid StatisticDTO statisticDTO) {
        Statistic statistic = statisticService.create(modelMapper.map(statisticDTO, Statistic.class));
        StatisticDTO response = modelMapper.map(statistic, StatisticDTO.class);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<StatisticDTO> deleteAll() {
        statisticService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

