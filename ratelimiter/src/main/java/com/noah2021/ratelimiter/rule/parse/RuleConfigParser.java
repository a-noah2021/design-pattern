package com.noah2021.ratelimiter.rule.parse;

import com.noah2021.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-06 22:40
 **/
public interface RuleConfigParser {

    RuleConfig parse(InputStream in);
}
