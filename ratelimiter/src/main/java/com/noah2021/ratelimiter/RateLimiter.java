package com.noah2021.ratelimiter;

import com.noah2021.ratelimiter.alg.FixedTimeWindowRateLimiter;
import com.noah2021.ratelimiter.alg.RateLimitAlg;
import com.noah2021.ratelimiter.error.RateLimiterException;
import com.noah2021.ratelimiter.rule.ApiLimit;
import com.noah2021.ratelimiter.rule.RateLimiterRule;
import com.noah2021.ratelimiter.rule.RuleConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: design-pattern
 * @description: 一个支持各种算法的限流框架
 * @author: noah2021
 * @date: 2022-10-06 14:51
 **/
public class RateLimiter {

    private static final Logger log = LoggerFactory.getLogger(RateLimiter.class);

    private ConcurrentHashMap<String, RateLimitAlg> counters = new ConcurrentHashMap<>();

    private RateLimiterRule rule;

    public RateLimiter() {
        // 读取配置文件ratelimiter-rule.yaml然后封装进RuleConfig
        InputStream in = null;
        RuleConfig ruleConfig = null;
        try {
            in = this.getClass().getResourceAsStream("/ratelimiter-rule.yaml");
            if (in != null) {
                Yaml yaml = new Yaml();
                ruleConfig = yaml.loadAs(in, RuleConfig.class);
            }
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("close file error:{}", e);
                }
            }
        }
        // TODO:将限流规则构建成支持快速查找的数据结构RateLimitRule
//        this.rule = new RateLimiterRule(ruleConfig);
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
