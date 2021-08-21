package com.jb.couponSpring.clr;

import com.jb.couponSpring.beans.*;
import com.jb.couponSpring.jobs.CouponExpirationDailyJob;
import com.jb.couponSpring.repos.CompanyRepository;
import com.jb.couponSpring.repos.CouponRepository;
import com.jb.couponSpring.services.*;
import com.jb.couponSpring.util.ArtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@Component
@Order(6)
@RequiredArgsConstructor
public class UseCaseMainTest implements CommandLineRunner {

    private final LoginManager loginManager;
    private AdminService adminService;
    private CompanyService companyService;
    private CustomerService customerService;

    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;

    private final CouponExpirationDailyJob couponExpirationDailyJob;

    @Override
    public void run(String... args) throws Exception {

        String email,password;

        System.out.println(">---------------- UseCaseMainTest ------------------");

        System.out.println(">---------------- UseCaseMainTest::Admin Login ------------------");

        email = "admin@admin.com";
        password = "admin";
        try {
            adminService = (AdminServiceImpl)loginManager.login(email,password, ClientType.ADMINISTRATOR);
        } catch (Exception e) {
            System.out.println("Unable to login, wrong administrator credentials");
        }

        System.out.println(ArtUtils.ADMIN_FACADE);

        try {
            testAdminService();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(">---------------- UseCaseMainTest::Company Login ------------------");

        email = "golf.co.il";
        password = "golf";
        try {
            companyService = (CompanyServiceImpl)loginManager.login(email,password, ClientType.COMPANY);
        } catch (Exception e) {
            System.out.println("Unable to login, wrong company credentials");
        }

        System.out.println(ArtUtils.COMPANY_FACADE);

        try {
            testCompanyService();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(">---------------- UseCaseMainTest::Customer Login ------------------");

        email = "adi@gmail.com";
        password = "123";
        try {
            customerService = (CustomerServiceImpl) loginManager.login(email,password, ClientType.CUSTOMER);
        } catch (Exception e) {
            System.out.println("Unable to login, wrong customer credentials");
        }

        System.out.println(ArtUtils.CUSTOMER_FACADE);

        try {
            testCustomerService();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(">---------------- UseCaseMainTest::CouponExpirationDailyJob ------------------");
        try {
            testDailyJob();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }


    }

    private void testDailyJob() {

        // test data for daily job
        Coupon p1 = Coupon.builder()
                .title("Test 1")
                .description("Test 1")
                .amount(50)
                .price(500)
                .category(Category.VACATION)
                .image("http://test1...")
                .startDate(Date.valueOf(LocalDate.now().minusDays(7)))
                .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
                .company(companyRepository.getById(2))
                .build();
        Coupon p2 = Coupon.builder()
                .title("Test 2")
                .description("Test 2")
                .amount(50)
                .price(500)
                .category(Category.VACATION)
                .image("http://test2...")
                .startDate(Date.valueOf(LocalDate.now().minusDays(7)))
                .endDate(Date.valueOf(LocalDate.now().plusDays(1)))
                .company(companyRepository.getById(2))
                .build();
        couponRepository.save(p1);
        couponRepository.save(p2);
        p1 = couponRepository.findByCompanyIdAndTitle(2,"Test 1");
        p2 = couponRepository.findByCompanyIdAndTitle(2,"Test 2");
        System.out.println(">---------------- testDailyJob::Coupon added before delete ------------------");
        System.out.println(p1);
        System.out.println(p2);
        try {
            customerService.purchaseCoupon(p1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            customerService.purchaseCoupon(p2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            couponExpirationDailyJob.setExpirationDate(Date.valueOf(LocalDate.now().plusDays(2)));
            couponExpirationDailyJob.removeExpiredCoupons();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(">---------------- testDailyJob::Coupon after delete ------------------");
        couponRepository.findAll().forEach(System.out::println);
        if (couponRepository.isPurchaseExists(customerService.getCustomerDetails().getId(), p1.getId()) == 0) {
            System.out.println("******* Purchase also deleted - coupon test 1 ******* ");
        } else {
            System.out.println("******* Purchase also deleted - coupon test 1  ******* ");
        }
        if (couponRepository.isPurchaseExists(customerService.getCustomerDetails().getId(), p2.getId()) == 0) {
            System.out.println("******* Purchase also deleted - coupon test 2 ******* ");
        } else {
            System.out.println("******* Purchase also deleted - coupon test 2  ******* ");
        }
    }

    private void testCustomerService() {

        System.out.println("\n>>>----- add coupon purchase -----");
        Coupon p = Coupon.builder()
                .title("Club Med 25%")
                .description("Club Med 25%")
                .amount(50)
                .price(500)
                .category(Category.VACATION)
                .image("http://clubmed25...")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusMonths(1)))
                .company(companyRepository.getById(2))
                .build();
        couponRepository.save(p);

        try {
            customerService.purchaseCoupon(couponRepository.getById(6));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void testCompanyService() throws SQLException {
        System.out.println("\n>>>----- try to add coupon with the same title -----");
        Coupon p1 = Coupon.builder()
                .title("golf 50%")
                .description("fox 50%")
                .amount(100)
                .price(50.99)
                .category(Category.FASHION)
                .image("http://fox50...")
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
        p1.setTitle("fox 50%");
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
        p1.setId(5);
        p1.setTitle("fox 50% updated");
        try {
            companyService.updateCoupon(p1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n>>>----- print all company coupons -----");
        companyService.getCompanyCoupons().forEach(System.out::println);
    }

    private void testAdminService() {

        System.out.println(">----- try to add company with existing name");
        Company c1 = Company.builder()
                .email("golf.co.il")
                .password("fox")
                .name("Golf")
                .build();
        System.out.println(c1);
        try {
            adminService.addCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        c1.setName("Fox");
        System.out.println(">----- try to add company with existing email");

        try {
            adminService.addCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        c1.setEmail("fox.co.il");
        try {
            adminService.addCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        adminService.getAllCompanies().forEach(System.out::println);

        System.out.println(">----- try to update company that does not exist");
        c1.setId(999);
        try {
            adminService.updateCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(">----- try to update company name");
        int id = 5;
        c1 = adminService.getSingleCompany(id);
        c1.setName("test");
        try {
            adminService.updateCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        c1 = adminService.getSingleCompany(id);
        System.out.println(">----- test a good update");
        c1.setEmail("golfandco.co.il");
        try {
            adminService.updateCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminService.getAllCompanies().forEach(System.out::println);


        System.out.println(">-------Test delete company ------------------");
        System.out.println("delete company -> " + adminService.getSingleCompany(id));
        try {
            adminService.deleteCompany(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminService.getAllCompanies().forEach(System.out::println);


        System.out.println(">------Test add customer ------------------");
        System.out.println(">----- try to add customer with existing email");

        Customer s1 = Customer.builder()
                .firstName("Yosi")
                .lastName("Shetrit")
                .email("adi@gmail.com")
                .password("123")
                .build();
        System.out.println(s1);
        try {
            adminService.addCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(">----- try a good add customer");
        s1.setEmail("yosi@gmail.com");
        try {
            adminService.addCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminService.getAllCustomers().forEach(System.out::println);

        System.out.println(">------Test update customer ------------------");
        System.out.println(">----- try to update customer that does not exist");
        s1.setId(999);
        try {
            adminService.updateCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        s1 = adminService.getSingleCustomer("yosi@gmail.com","123");
        System.out.println(">----- test a good update");
        s1.setEmail("yosiUPD@gmail.com");
        try {
            adminService.updateCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        adminService.getAllCustomers().forEach(System.out::println);

        System.out.println(">------Test delete customer and coupons ------------------");
        try {
            adminService.deleteCustomer(s1.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(">----- list of customers after delete");
        adminService.getAllCustomers().forEach(System.out::println);
    }
}
