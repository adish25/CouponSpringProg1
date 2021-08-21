package com.jb.couponSpring.clr;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Company;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.repos.CompanyRepository;
import com.jb.couponSpring.repos.CouponRepository;
import com.jb.couponSpring.repos.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@Order(1)
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(">---------------- Bootstrap :: preparing test data ------------------");
        Company c1 = Company.builder()
                .email("golf.co.il")
                .password("golf")
                .name("Golf")
                .build();

        Company c2 = Company.builder()
                .email("taizu.co.il")
                .password("taizu")
                .name("Taizu")
                .build();

        Company c3 = Company.builder()
                .email("flyeast.co.il")
                .password("flyeast")
                .name("Fly East")
                .build();

        companyRepository.saveAll(Arrays.asList(c1,c2,c3));
        companyRepository.findAll().forEach(System.out::println);

        Customer s1 = Customer.builder()
                .firstName("Adi")
                .lastName("Shetrit")
                .email("adi@gmail.com")
                .password("123")
                .build();

        Customer s2 = Customer.builder()
                .firstName("Yuval")
                .lastName("Shetrit")
                .email("yuval@gmail.com")
                .password("123")
                .build();

        Customer s3 = Customer.builder()
                .firstName("Maor")
                .lastName("Shetrit")
                .email("maor@gmail.com")
                .password("123")
                .build();

        Customer s4 = Customer.builder()
                .firstName("Alon")
                .lastName("Shetrit")
                .email("alon@gmail.com")
                .password("123")
                .build();

        customerRepository.saveAll(Arrays.asList(s1,s2,s3,s4));
        customerRepository.findAll().forEach(System.out::println);

        Coupon p1 = Coupon.builder()
                .title("free drink")
                .description("taizu free drink")
                .amount(50)
                .price(30)
                .category(Category.FOOD)
                .image("http://t...")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusWeeks(2)))
                .company(companyRepository.getById(2))
                .build();

        Coupon p2 = Coupon.builder()
                .title("golf 50%")
                .description("golf 50%")
                .amount(100)
                .price(50.99)
                .category(Category.FASHION)
                .image("http://golf50...")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusMonths(1)))
                .company(companyRepository.getById(1))
                .build();

        Coupon p3 = Coupon.builder()
                .title("fly thai 50%")
                .description("fly thai 50%")
                .amount(30)
                .price(300)
                .category(Category.VACATION)
                .image("http://flythai...")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusMonths(1)))
                .company(companyRepository.getById(3))
                .build();

        Coupon p4 = Coupon.builder()
                .title("fly japan 50%")
                .description("fly japan 50%")
                .amount(50)
                .price(500)
                .category(Category.VACATION)
                .image("http://flyjapan...")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusMonths(1)))
                .company(companyRepository.getById(3))
                .build();


        couponRepository.saveAll(Arrays.asList(p1,p2));
        couponRepository.findAll().forEach(System.out::println);
    }
}
