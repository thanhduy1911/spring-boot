package com.duyhvt.springcoredemo.config;

import com.duyhvt.springcoredemo.common.Coach;
import com.duyhvt.springcoredemo.common.SwimCoach;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SportConfig {
    @Bean("aquatic")
    public Coach swimCoach() {
        return new SwimCoach();
    }
}
