package com.noah2021.ratelimiter.alg;

import com.google.common.base.Stopwatch;
import com.noah2021.ratelimiter.error.RateLimiterException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: design-pattern
 * @description: 简易版的固定时间窗口限流算法
 * @author: noah2021
 * @date: 2022-10-07 14:53
 **/
@Slf4j
public abstract class RateLimitAlg {

    public static final long TRY_LOCK_TIMEOUT = 200L;
    public Stopwatch stopwatch;
    public AtomicInteger count = new AtomicInteger(0);
    public int limit;
    public Lock lock = new ReentrantLock();

    public RateLimitAlg(int limit) {
        this(limit, Stopwatch.createStarted());
    }

    public RateLimitAlg(int limit, Stopwatch stopwatch) {
        this.limit = limit;
        this.stopwatch = stopwatch;
    }

    public abstract boolean tryAcquire() throws RateLimiterException;
}
