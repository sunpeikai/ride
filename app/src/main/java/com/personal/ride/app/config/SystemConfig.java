package com.personal.ride.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangqingqing
 * @version SystemConfig, v0.1 2019/4/3 14:30
 */
@Configuration
@Data
public class SystemConfig {


    @Value("${app.filter.aes.localKey}")
    private String localKey;

    @Value("${app.filter.aes.localIV}")
    private String localIV;

    @Value("${ride.best.host}")
    private String bestHost;

}
