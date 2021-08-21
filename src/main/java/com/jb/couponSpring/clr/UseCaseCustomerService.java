package com.jb.couponSpring.clr;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.repos.CompanyRepository;
import com.jb.couponSpring.repos.CouponRepository;
import com.jb.couponSpring.services.CustomerService;
import com.jb.couponSpring.services.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Order(4)
@RequiredArgsConstructor
public class UseCaseCustomerService implements CommandLineRunner {

    private final CustomerService customerService;
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">---------------- UseCaseCustomerService ------------------");

        System.out.println(">---------------- UseCaseCustomerService::Test Login ------------------");

        Customer r;
        if (((CustomerServiceImpl)customerService).login("adi@gmail.com","123")) {
            r = customerService.getCustomerByEmailPassword("adi@gmail.com","123");
            System.out.println("Wellcome !!!" + r);
        } else {
            System.out.println("Wrong customer user / password !!!");
        }

        System.out.println("\n>>>----- add coupon purchase -----");
        Coupon p = Coupon.builder()
                .title("fly USA 50%")
                .description("fly USA 50%")
                .amount(50)
                .price(500)
                .category(Category.VACATION)
                .image("http://flyusa...")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusMonths(1)))
                .company(companyRepository.getById(2))
                .build();
        couponRepository.save(p);

        try {
            customerService.purchaseCoupon(couponRepository.getById(4));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
