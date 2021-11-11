package com.mercadolivre.proxy.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateLimiterModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String ip;
	private String path;
	private Integer windowInSeconds;
	private Integer maxRequestsInWindow;
	private Integer timeBetweenCalls;
}