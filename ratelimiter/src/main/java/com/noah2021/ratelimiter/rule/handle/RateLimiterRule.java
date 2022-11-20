package com.noah2021.ratelimiter.rule.handle;

import com.noah2021.ratelimiter.exception.RateLimiterException;
import com.noah2021.ratelimiter.rule.entity.ApiLimit;
import com.noah2021.ratelimiter.rule.entity.RuleConfig;

import java.util.List;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-07 14:53
 **/
public interface RateLimiterRule {

    /**
     * get limit info of one url.
     */
    ApiLimit getLimit(String appId, String api) throws RateLimiterException;

    /**
     * add one limit for one app.
     */
    void addLimit(String appId, ApiLimit apiLimit) throws RateLimiterException;

    /**
     * add limits for one app.
     */
    void addLimits(String appId, List<ApiLimit> limits) throws RateLimiterException;

    /**
     * override old rule.
     */
    void rebuildRule(RuleConfig ruleConfig) throws RateLimiterException;

    /**
     * add rule into the existing rule.
     */
    void addRule(RuleConfig ruleConfig) throws RateLimiterException;
}
