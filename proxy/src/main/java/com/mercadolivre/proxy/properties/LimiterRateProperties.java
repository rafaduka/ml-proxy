package com.mercadolivre.proxy.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ratelimit")
@Getter
@Setter
public class LimiterRateProperties {
    private int ipInterval;
    private int pathInterval;
    private int ipAndPathInterval;

    private int ipMaxRequest;
    private int pathMaxRequest;
    private int ipAndPathMaxRequest;
}