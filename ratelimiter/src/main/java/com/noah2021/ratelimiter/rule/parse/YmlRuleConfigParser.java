package com.noah2021.ratelimiter.rule.parse;

import com.noah2021.ratelimiter.rule.RuleConfig;

import java.io.InputStream;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 06:55
 **/
public class YmlRuleConfigParser implements RuleConfigParser{
    @Override
    public RuleConfig parse(InputStream in) {
        System.out.println("This is YmlRuleConfigParser");
        return null;
    }
}
