package com.noah2021.ratelimiter.rule;

import com.noah2021.ratelimiter.error.EmRateLimiterError;
import com.noah2021.ratelimiter.error.RateLimiterException;
import com.noah2021.ratelimiter.rule.source.UniformRuleConfigMapping;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 22:05
 **/
public class UrlRateLimitRule implements RateLimiterRule{
    /**
     * store <appId, limit rules> pairs.
     */
    private volatile ConcurrentHashMap<String, AppUrlRateLimitRule> limitRules =
            new ConcurrentHashMap<>();

    public UrlRateLimitRule() {
    }

    @Override
    public void addRule(UniformRuleConfigMapping uniformRuleConfigMapping) throws RateLimiterException {
        if (uniformRuleConfigMapping == null) {
            return;
        }
        List<UniformRuleConfigMapping.UniformRuleConfig> uniformRuleConfigs =
                uniformRuleConfigMapping.getConfigs();
        try {
            for (UniformRuleConfigMapping.UniformRuleConfig uniformRuleConfig : uniformRuleConfigs) {
                String appId = uniformRuleConfig.getAppId();
                addLimits(appId, uniformRuleConfig.getLimits());
            }
        } catch (RateLimiterException e) {
            throw new RateLimiterException(EmRateLimiterError.CONFIGURATION_RESOLVE_ERR,"rule configuration is invalid: "+e);
        }
    }

    @Override
    public ApiLimit getLimit(String appId, String urlPath) throws RateLimiterException {
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(urlPath)) {
            return null;
        }

        AppUrlRateLimitRule appUrlRateLimitRule = limitRules.get(appId);
        if (appUrlRateLimitRule == null) {
            return null;
        }

        ApiLimit apiLimit = appUrlRateLimitRule.getLimitInfo(urlPath);
        return apiLimit;
    }

    @Override
    public void addLimit(String appId, ApiLimit apiLimit) throws RateLimiterException {
        if (StringUtils.isEmpty(appId) || apiLimit == null) {
            return;
        }

        AppUrlRateLimitRule newTrie = new AppUrlRateLimitRule();
        AppUrlRateLimitRule trie = limitRules.putIfAbsent(appId, newTrie);
        if (trie == null) {
            newTrie.addLimitInfo(apiLimit);
        } else {
            trie.addLimitInfo(apiLimit);
        }
    }

    @Override
    public void addLimits(String appId, List<ApiLimit> limits) throws RateLimiterException {
        AppUrlRateLimitRule newTrie = new AppUrlRateLimitRule();
        AppUrlRateLimitRule trie = limitRules.putIfAbsent(appId, newTrie);
        if (trie == null) {
            trie = newTrie;
        }
        for (ApiLimit apiLimit : limits) {
            trie.addLimitInfo(apiLimit);
        }
    }

    @Override
    public void rebuildRule(UniformRuleConfigMapping uniformRuleConfigMapping) throws RateLimiterException {
        ConcurrentHashMap<String, AppUrlRateLimitRule> newLimitRules = new ConcurrentHashMap<>();
        List<UniformRuleConfigMapping.UniformRuleConfig> uniformRuleConfigs =
                uniformRuleConfigMapping.getConfigs();
        for (UniformRuleConfigMapping.UniformRuleConfig uniformRuleConfig : uniformRuleConfigs) {
            String appId = uniformRuleConfig.getAppId();
            AppUrlRateLimitRule appUrlRateLimitRule = new AppUrlRateLimitRule();
            newLimitRules.put(appId, appUrlRateLimitRule);
            try {
                for (ApiLimit apiLimit : uniformRuleConfig.getLimits()) {
                    appUrlRateLimitRule.addLimitInfo(apiLimit);
                }
            } catch (RateLimiterException e) {
                throw new RateLimiterException(EmRateLimiterError.CONFIGURATION_RESOLVE_ERR,"rule configuration is invalid: "+e);
            }
        }
        limitRules = newLimitRules;
    }

}
