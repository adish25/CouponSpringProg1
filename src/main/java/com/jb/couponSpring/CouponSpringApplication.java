package com.jb.couponSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CouponSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponSpringApplication.class, args);
		System.out.println("coupon spring project is running....");
	}

}
