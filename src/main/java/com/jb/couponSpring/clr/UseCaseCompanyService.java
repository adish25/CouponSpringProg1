package com.jb.couponSpring.clr;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Company;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.repos.CompanyRepository;
import com.jb.couponSpring.services.CompanyService;
import com.jb.couponSpring.services.CompanyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Order(3)
@RequiredArgsConstructor

public class UseCaseCompanyService implements CommandLineRunner {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(">---------------- UseCaseCompanyService ------------------");

        System.out.println(">---------------- UseCaseCompanyService::Test Login ------------------");

        Company c;
        if (((CompanyServiceImpl)companyService).login("golf.co.il", "golf")) {
            c = companyService.getCompanyByEmailPassword("golf.co.il", "golf");
            System.out.println("Wellcome !!!" + c);
        } else {
            System.out.println("Wrong company user / password !!!");
        }

        System.out.println("\n>>>----- try to add coupon with the same title -----");
        Coupon p1 = Coupon.builder()
                .title("golf 50%")
                .description("golf 50% new")
                .amount(100)
                .price(50.99)
                .category(Category.FASHION)
                .image("http://golf50new...")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusMonths(1)))
                .company(companyRepository.getById(1))
                .build();

        try {
          companyService.addCoupon(p1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>>----- try to add coupon after changing the title -----");
        p1.setTitle("golf 50% new");
        try {
            companyService.addCoupon(p1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>>----- print all company coupons -----");
        companyService.getCompanyCoupons().forEach(System.out::println);

        System.out.println("\n>>>----- print all company coupons, by category -----");
        companyService.getCompanyCoupons(Category.FASHION).forEach(System.out::println);

        System.out.println("\n>>>----- try to update coupon's company id -----");
        p1.getCompany().setId(999);
        try {
            companyService.updateCoupon(p1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>>----- try to update a coupons that does not exists -----");
        p1.getCompany().setId(1);
        p1.setId(999);
        try {
            companyService.updateCoupon(p1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>>----- test a good update of coupon title -----");
        p1.setId(3);
        p1.setTitle("golf 50% updated");
        try {
            companyService.updateCoupon(p1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>>----- print all company coupons -----");
        companyService.getCompanyCoupons().forEach(System.out::println);
    }
}
