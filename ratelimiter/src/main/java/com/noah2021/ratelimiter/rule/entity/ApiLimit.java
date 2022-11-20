package com.noah2021.ratelimiter.rule.entity;

import lombok.Data;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-06 14:53
 **/
@Data
public class ApiLimit {

    public static final int DEFAULT_TIME_UNIT = 1;
    private String api;
    private int limit;
    private int unit = DEFAULT_TIME_UNIT;

    public ApiLimit() {
    }

    public ApiLimit(String api, int limit) {
        this(api, limit, DEFAULT_TIME_UNIT);
    }

    public ApiLimit(String api, int limit, int unit) {
        this.api = api;
        this.limit = limit;
        this.unit = unit;
    }
}
