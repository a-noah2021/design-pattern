import com.noah2021.ratelimiter.alg.FixedTimeWindowRateLimiter;
import com.noah2021.ratelimiter.alg.LeakyBucketLimiter;
import com.noah2021.ratelimiter.alg.RateLimitAlg;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.lang.Thread.currentThread;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-07 23:38
 **/
public class RateLimiterAlgTest {

    @Test
    public void fixedTimeWindowRateLimiterTest() throws InterruptedException {
        RateLimitAlg fixedTimeWindowRateLimiter = new FixedTimeWindowRateLimiter(1);
        // RateLimiter rateLimiter = RateLimiter.create(1);
        while (true) {
            // 在访问该方法之前首先要进行 RateLimiter 的获取，返回值为实际的获取等待开销时间
            System.out.println(currentThread() + ": elapsed seconds " + LocalDateTime.now() + " tryAcquire ->" + fixedTimeWindowRateLimiter.tryAcquire());
            Thread.sleep(200);
        }
    }

    @Test
    public void leakyBucketLimiterTest() {
        // 定义一个 RateLimiter ，单位时间（默认为秒）的设置为 0.5【访问速率为 0.5 / 秒】
        RateLimitAlg leakyBucketLimiter = new LeakyBucketLimiter(10, 1);
        // RateLimiter rateLimiter = RateLimiter.create(1);
        //private static RateLimiter rateLimiter = new T
        while (true) {
            // 在访问该方法之前首先要进行 RateLimiter 的获取，返回值为实际的获取等待开销时间
            leakyBucketLimiter.tryAcquire();
            System.out.println(currentThread() + ": elapsed seconds " + LocalDateTime.now());
        }
    }
}
