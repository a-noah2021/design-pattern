package com.noah2021.ratelimiter.rule.parse;

import com.noah2021.ratelimiter.rule.entity.RuleConfig;

import java.io.InputStream;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-06 22:43
 **/
public class JsonRuleConfigParser implements RuleConfigParser{
    @Override
    public RuleConfig parse(InputStream in) {
        System.out.println("This is JsonRuleConfigParser");
        return null;
    }
}
