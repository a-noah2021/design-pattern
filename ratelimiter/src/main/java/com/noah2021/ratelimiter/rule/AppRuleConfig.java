package com.noah2021.ratelimiter.rule;

import lombok.Data;

import java.util.List;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-06 15:05
 **/
@Data
public class AppRuleConfig {

    private String appId;
    private List<ApiLimit> limits;

    public AppRuleConfig() {
    }

    public AppRuleConfig(String appId, List<ApiLimit> limits) {
        this.appId = appId;
        this.limits = limits;
    }
}
