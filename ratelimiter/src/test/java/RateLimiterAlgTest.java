import com.noah2021.ratelimiter.alg.FixedTimeWindowRateLimiter;
import com.noah2021.ratelimiter.alg.LeakyBucketLimiter;
import com.noah2021.ratelimiter.alg.RateLimitAlg;
import com.noah2021.ratelimiter.error.RateLimiterException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void fixedTimeWindowRateLimiterTest() throws RateLimiterException, InterruptedException {
        RateLimitAlg fixedTimeWindowRateLimiter = new FixedTimeWindowRateLimiter(1);
        while (true) {
            // 在访问该方法之前首先要进行 RateLimiter 的获取，返回值为实际的获取等待开销时间
            System.out.println(Thread.currentThread() + ": elapsed seconds " + LocalDateTime.now() + " tryAcquire ->" + fixedTimeWindowRateLimiter.tryAcquire());
            Thread.sleep(200);
        }
    }

    @Test
    public void leakyBucketLimiterTest() throws RateLimiterException {
        RateLimitAlg leakyBucketLimiter = new LeakyBucketLimiter(10, 1);
        while (true) {
            // 在访问该方法之前首先要进行 RateLimiter 的获取，返回值为实际的获取等待开销时间
            leakyBucketLimiter.tryAcquire();
            System.out.println(Thread.currentThread() + ": elapsed seconds " + LocalDateTime.now());
        }
    }

}
