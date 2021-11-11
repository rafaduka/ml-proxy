package com.mercadolivre.proxy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDTO {

    private long total;
    private long success;
    private long error;
    private long rateLimit;
    private long duration;
    private Date date;
}
