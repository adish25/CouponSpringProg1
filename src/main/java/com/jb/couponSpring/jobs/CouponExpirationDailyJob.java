package com.jb.couponSpring.jobs;

import com.jb.couponSpring.repos.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponExpirationDailyJob {

    private final CouponRepository couponRepository;

    @Scheduled(fixedRate = 2000)
    public void removeExpiredCoupons () {
        System.out.println("*********** CouponExpirationDailyJob is running **************");
    }

}
