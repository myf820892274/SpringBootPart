package com.yf.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations="classpath:applicationContext-monitor.xml")
public class DruidStatInterceptor {
}
