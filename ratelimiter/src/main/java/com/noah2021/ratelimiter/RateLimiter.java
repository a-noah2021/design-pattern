package com.noah2021.ratelimiter;

import com.noah2021.ratelimiter.alg.FixedTimeWindowRateLimiter;
import com.noah2021.ratelimiter.alg.RateLimitAlg;
import com.noah2021.ratelimiter.exception.RateLimiterException;
import com.noah2021.ratelimiter.rule.entity.ApiLimit;
import com.noah2021.ratelimiter.rule.handle.RateLimiterRule;
import com.noah2021.ratelimiter.rule.entity.RuleConfig;
import com.noah2021.ratelimiter.rule.handle.UrlRateLimitRule;
import com.noah2021.ratelimiter.rule.load.FileRuleConfigSource;
import com.noah2021.ratelimiter.rule.load.RuleConfigSource;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: design-pattern
 * @description: 一个支持各种算法的限流框架
 * @author: noah2021
 * @date: 2022-10-06 14:51
 **/
@Slf4j
public class RateLimiter {

    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();

    private RateLimiterRule rule;

    public RateLimiter() throws RateLimiterException {
        RuleConfigSource ruleConfigSource = new FileRuleConfigSource();
        RuleConfig ruleConfig = ruleConfigSource.load();
        //将限流规则构建成支持快速查找的数据结构RateLimitRule
        this.rule = new UrlRateLimitRule(ruleConfig);
    }

    /**
     * 暴露给用户用的顶层接口,仅支持HTTP接口的单机限流
     * @param appId
     * @param url
     * @return boolean
     * @throws InterruptedException
     */
    public boolean limit(String appId, String url) throws RateLimiterException {
        ApiLimit apiLimit = rule.getLimit(appId, url);
        if (apiLimit == null) {
            return true;
        }
        // 获取api对应在内存中的限流计数器（rateLimitCounter）
        String counterKey = appId + ":" + apiLimit.getApi();
        RateLimitAlg rateLimitCounter = counters.get(counterKey);
        if (rateLimitCounter == null) {
            RateLimitAlg newRateLimitCounter = new FixedTimeWindowRateLimiter(apiLimit.getLimit());
            rateLimitCounter = counters.putIfAbsent(counterKey, newRateLimitCounter);
            if (rateLimitCounter == null) {
                rateLimitCounter = newRateLimitCounter;
            }
        }
        // 判断是否限流
        return rateLimitCounter.tryAcquire();
    }
}
