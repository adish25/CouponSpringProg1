package com.jb.couponSpring.clr;

import com.jb.couponSpring.beans.ClientType;
import com.jb.couponSpring.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(5)
@RequiredArgsConstructor
public class UseCaseLoginManager implements CommandLineRunner {

    private final LoginManager loginManager;
    private AdminService adminService;
    private CompanyService companyService;
    private CustomerService customerService;

    @Override
    public void run(String... args) throws Exception {

        String email,password;

        System.out.println(">---------------- UseCaseLoginManager ------------------");

        System.out.println(">---------------- UseCaseLoginManager::Test Admin Login ------------------");

        email = "admin@admin.com";
        password = "admin";
        try {
            adminService = (AdminServiceImpl)loginManager.login(email,password, ClientType.ADMINISTRATOR);
            adminService.getAllCompanies().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Unable to login, wrong administrator credentials");
        }

        System.out.println(">---------------- UseCaseLoginManager::Test Company Login ------------------");
        email = "golf.co.il";
        password = "golf";
        try {
            companyService = (CompanyServiceImpl)loginManager.login(email,password, ClientType.COMPANY);
            System.out.println(companyService.getCompanyByEmailPassword(email,password));
        } catch (Exception e) {
            System.out.println("Unable to login, wrong company credentials");
        }

        System.out.println(">---------------- UseCaseLoginManager::Test Customer Login ------------------");
        email = "adi@gmail.com";
        password = "123";
        try {
            customerService = (CustomerServiceImpl) loginManager.login(email,password, ClientType.CUSTOMER);
            System.out.println(customerService.getCustomerByEmailPassword(email,password));
        } catch (Exception e) {
            System.out.println("Unable to login, wrong customer credentials");
        }

        System.out.println(">---------------- UseCaseLoginManager::Test another Company Login ------------------");
        email = "taizu.co.il";
        password = "taizu";
        try {
            companyService = (CompanyServiceImpl)loginManager.login(email,password, ClientType.COMPANY);
            System.out.println(companyService.getCompanyByEmailPassword(email,password));
        } catch (Exception e) {
            System.out.println("Unable to login, wrong company credentials");
        }

        System.out.println(">---------------- UseCaseLoginManager::Test another Customer Login ------------------");
        email = "maor@gmail.com";
        password = "123";
        try {
            customerService = (CustomerServiceImpl) loginManager.login(email,password, ClientType.CUSTOMER);
            System.out.println(customerService.getCustomerByEmailPassword(email,password));
        } catch (Exception e) {
            System.out.println("Unable to login, wrong customer credentials");
        }

    }
}
