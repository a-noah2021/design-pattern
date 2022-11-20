package com.noah2021.ratelimiter.rule.load;

import com.noah2021.ratelimiter.rule.entity.RuleConfig;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-06 23:21
 **/
public interface RuleConfigSource {
    RuleConfig load();
}
