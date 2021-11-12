package com.mercadolivre.control.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {

    private Date date;
    private long success;
    private long fail;
    private long limited;
    private long duration;
    private long total;
}
