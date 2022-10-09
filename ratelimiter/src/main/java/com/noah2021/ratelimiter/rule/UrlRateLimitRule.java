package com.noah2021.ratelimiter.rule;

import com.noah2021.ratelimiter.error.EmRateLimiterError;
import com.noah2021.ratelimiter.error.RateLimiterException;
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

    public UrlRateLimitRule(){}

    public UrlRateLimitRule(RuleConfig ruleConfig) throws RateLimiterException {
        addRule(ruleConfig);
    }


    @Override
    public void addRule(RuleConfig ruleConfig) throws RateLimiterException {
        if (ruleConfig == null) {
            return;
        }
        List<AppRuleConfig> appRuleConfigs =
                ruleConfig.getConfigs();
        try {
            for (AppRuleConfig appRuleConfig : appRuleConfigs) {
                String appId = appRuleConfig.getAppId();
                addLimits(appId, appRuleConfig.getLimits());
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
    public void rebuildRule(RuleConfig ruleConfig) throws RateLimiterException {
        ConcurrentHashMap<String, AppUrlRateLimitRule> newLimitRules = new ConcurrentHashMap<>();
        List<AppRuleConfig> appRuleConfigs =
                ruleConfig.getConfigs();
        for (AppRuleConfig appRuleConfig : appRuleConfigs) {
            String appId = appRuleConfig.getAppId();
            AppUrlRateLimitRule appUrlRateLimitRule = new AppUrlRateLimitRule();
            newLimitRules.put(appId, appUrlRateLimitRule);
            try {
                for (ApiLimit apiLimit : appRuleConfig.getLimits()) {
                    appUrlRateLimitRule.addLimitInfo(apiLimit);
                }
            } catch (RateLimiterException e) {
                throw new RateLimiterException(EmRateLimiterError.CONFIGURATION_RESOLVE_ERR,"rule configuration is invalid: "+e);
            }
        }
        limitRules = newLimitRules;
    }

}
