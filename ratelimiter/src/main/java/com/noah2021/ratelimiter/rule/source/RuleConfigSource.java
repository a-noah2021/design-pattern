package com.noah2021.ratelimiter.rule.source;

import com.noah2021.ratelimiter.rule.RuleConfig;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-07 22:40
 **/
public interface RuleConfigSource {
    RuleConfig load();
}
