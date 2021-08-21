package com.jb.couponSpring.jobs;

import com.jb.couponSpring.repos.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class CouponExpirationDailyJob {

    private final CouponRepository couponRepository;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void removeExpiredCoupons () {
        System.out.println("*********** CouponExpirationDailyJob is running **************");
        couponRepository.deleteExpiredPurchases(Date.valueOf(LocalDate.now()));
        couponRepository.deleteExpiredCoupons(Date.valueOf(LocalDate.now()));
        System.out.println("*********** CouponExpirationDailyJob :: coupons after delete **************");
        couponRepository.findAll().forEach(System.out::println);
    }

}
