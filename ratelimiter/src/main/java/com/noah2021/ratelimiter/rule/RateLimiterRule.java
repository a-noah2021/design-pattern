package com.noah2021.ratelimiter.rule;

import com.noah2021.ratelimiter.error.RateLimiterException;
import com.noah2021.ratelimiter.rule.source.UniformRuleConfigMapping;

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
     *
     * @param appId
     * @param api
     * @return
     * @throws RateLimiterException
     */
    ApiLimit getLimit(String appId, String api) throws RateLimiterException;

    /**
     * add one limit for one app.
     *
     * @param appId
     * @param apiLimit
     * @throws RateLimiterException
     */
    void addLimit(String appId, ApiLimit apiLimit) throws RateLimiterException;

    /**
     * add limits for one app.
     *
     * @param limits
     * @throws RateLimiterException
     */
    void addLimits(String appId, List<ApiLimit> limits) throws RateLimiterException;

    /**
     * override old rule.
     *
     * @param uniformRuleConfigMapping
     */
    void rebuildRule(UniformRuleConfigMapping uniformRuleConfigMapping) throws RateLimiterException;

    /**
     * add rule into the existing rule.
     *
     * @param uniformRuleConfigMapping
     */
    void addRule(UniformRuleConfigMapping uniformRuleConfigMapping) throws RateLimiterException;
}
