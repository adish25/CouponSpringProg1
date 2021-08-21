package com.jb.couponSpring.jobs;

import com.jb.couponSpring.repos.CouponRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Data
@RequiredArgsConstructor
public class CouponExpirationDailyJob {

    private final CouponRepository couponRepository;

    // Declaration to allow override of expiration date, for testing and other tasks
    private Date expirationDate = Date.valueOf(LocalDate.now());

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void removeExpiredCoupons () {
        System.out.println("*********** CouponExpirationDailyJob is running **************");
        couponRepository.deleteExpiredPurchases(expirationDate);
        couponRepository.deleteExpiredCoupons(expirationDate);
    }

}
