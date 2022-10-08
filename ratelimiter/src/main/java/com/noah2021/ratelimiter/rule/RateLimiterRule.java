package com.noah2021.ratelimiter.rule;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-07 14:53
 **/
public class RateLimiterRule {

    private RuleConfig ruleConfig;

    public RateLimiterRule(RuleConfig ruleConfig) {
        this.ruleConfig = ruleConfig;
    }

    public ApiLimit getLimit(String appId, String url) {
        return new ApiLimit();
    }
}
