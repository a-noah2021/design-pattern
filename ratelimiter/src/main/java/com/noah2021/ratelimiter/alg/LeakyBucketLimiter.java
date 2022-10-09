package com.noah2021.ratelimiter.alg;

import com.noah2021.ratelimiter.error.RateLimiterException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-07 23:12
 **/
@Slf4j
public class LeakyBucketLimiter implements RateLimitAlg {

    //桶的大小
    private int capacity;

    //定时流水
    public ScheduledThreadPoolExecutor constantFlow;

    //桶
    private BlockingQueue<Thread> bucket;

    public LeakyBucketLimiter(int capacity, double rate) {
        this.capacity = capacity;
        bucket = new LinkedBlockingQueue<>(capacity);
        double period = SECONDS.toMicros(1L) / rate;
        constantFlow = new ScheduledThreadPoolExecutor(1);
        constantFlow.scheduleAtFixedRate(() -> {
            try {
                LockSupport.unpark(bucket.take());
            } catch (InterruptedException e) {
                log.error("failed to take bucket，be interrupted");
            }
        }, 0, (long) period, TimeUnit.MICROSECONDS);
    }

    @Override
    public boolean tryAcquire() {
        try {
            bucket.put(Thread.currentThread());
            LockSupport.park();
        } catch (InterruptedException e) {
            log.error("failed to save bucket，be interrupted");
        }
        return true;
    }
}
