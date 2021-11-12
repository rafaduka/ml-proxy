package com.mercadolivre.control.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;


@Data
@NoArgsConstructor
public class Statistic {

    @Id
    private String id;
    private long total;
    private long success;
    private long fail;
    private long limited;
    private long duration;
    private Date date;

    public Statistic(long total, long success, long fail, long limited, long duration, Date date) {
        this.total = total;
        this.success = success;
        this.fail = fail;
        this.limited = limited;
        this.duration = duration;
        this.date = date;
    }
}
