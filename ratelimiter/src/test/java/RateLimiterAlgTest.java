import com.noah2021.ratelimiter.RateLimiter;
import com.noah2021.ratelimiter.alg.FixedTimeWindowRateLimiter;
import com.noah2021.ratelimiter.alg.LeakyBucketLimiter;
import com.noah2021.ratelimiter.alg.RateLimitAlg;
import com.noah2021.ratelimiter.exception.RateLimiterException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 * @program: design-pattern
 * @description
 * @author: noah2021
 * @date: 2022-10-07 23:38
 **/
public class RateLimiterAlgTest {

    @Test
    public void fixedTimeWindowRateLimiterTest() throws RateLimiterException, InterruptedException {
        RateLimitAlg fixedTimeWindowRateLimiter = new FixedTimeWindowRateLimiter(3);
        while (true) {
            System.out.println(Thread.currentThread() + ": elapsed seconds " + LocalDateTime.now() + " tryAcquire ->" + fixedTimeWindowRateLimiter.tryAcquire());
            Thread.sleep(200);
        }
    }

    @Test
    public void leakyBucketLimiterTest() throws RateLimiterException {
        RateLimitAlg leakyBucketLimiter = new LeakyBucketLimiter(10, 0.5);
        while (true) {
            leakyBucketLimiter.tryAcquire();
            System.out.println(Thread.currentThread() + ": elapsed seconds " + LocalDateTime.now());
        }
    }

    @Test
    public void rateLimiterTest() throws RateLimiterException {
        RateLimiter rateLimiter = new RateLimiter();
        int count = 100;
        while (count-- > 0) {
            rateLimiter.limit("app1", "/v1/order");
        }
    }

}
