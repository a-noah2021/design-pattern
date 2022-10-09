package com.noah2021.ratelimiter.alg;

import com.noah2021.ratelimiter.error.RateLimiterException;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: design-pattern
 * @description: 简易版的固定时间窗口限流算法
 * @author: noah2021
 * @date: 2022-10-07 14:53
 **/
public interface RateLimitAlg {

    boolean tryAcquire() throws RateLimiterException;

}
