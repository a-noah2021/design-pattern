package com.noah2021.ratelimiter.rule.entity;

import lombok.Data;

import java.util.List;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-07 15:15
 **/
@Data
public class RuleConfig {

    private List<AppRuleConfig> configs;

    public List<AppRuleConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<AppRuleConfig> configs) {
        this.configs = configs;
    }
}
