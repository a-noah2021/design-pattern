package com.noah2021.ratelimiter.alg;

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
public class LeakyBucketLimiter extends RateLimitAlg {
    //桶的大小
    private int capacity;

    //定时流水
    public ScheduledThreadPoolExecutor constantFlow;

    //桶
    private BlockingQueue<Thread> bucket;


    public LeakyBucketLimiter(int capacity, double rate) {
        super(1);
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


    public boolean tryAcquire() {
        try {
            bucket.put(currentThread());
            LockSupport.park();
        } catch (InterruptedException e) {
            log.error("failed to save bucket，be interrupted");
        }
        return true;
    }

    public static void main(String[] args) {
        // 定义一个 RateLimiter ，单位时间（默认为秒）的设置为 0.5【访问速率为 0.5 / 秒】
        RateLimitAlg leakyBucketLimiter = new LeakyBucketLimiter(10, 1);
        // RateLimiter rateLimiter = RateLimiter.create(1);
        //private static RateLimiter rateLimiter = new T
        for (; ; ) {
            // 在访问该方法之前首先要进行 RateLimiter 的获取，返回值为实际的获取等待开销时间
            leakyBucketLimiter.tryAcquire();
            System.out.println(currentThread() + ": elapsed seconds " + LocalDateTime.now());
        }
    }


}
