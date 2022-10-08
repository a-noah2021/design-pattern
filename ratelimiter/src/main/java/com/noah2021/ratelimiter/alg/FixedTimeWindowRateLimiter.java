package com.noah2021.ratelimiter.alg;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-07 23:10
 **/
@Slf4j
public class FixedTimeWindowRateLimiter extends RateLimitAlg {

    public FixedTimeWindowRateLimiter(int limit) {
        this(limit, Stopwatch.createStarted());
    }

    public FixedTimeWindowRateLimiter(int limit, Stopwatch stopwatch) {
        super(limit);
        this.limit = limit;
        this.stopwatch = stopwatch;
        this.lock = new ReentrantLock();
    }

    public boolean tryAcquire() {
        int addedCount = count.incrementAndGet();
        if (addedCount <= limit) {
            return true;
        }
        try {
            if (lock.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.MILLISECONDS)) {
                try {
                    if (stopwatch.elapsed(TimeUnit.MILLISECONDS) > TimeUnit.SECONDS.toMillis(1)) {
                        count.set(0);
                        stopwatch.reset().start();
                    }
                    addedCount = count.incrementAndGet();
                    return addedCount <= limit;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            log.error("tryAcquire() is interrupted by lock-time-out.", e);
        }
        return false;
    }
}
