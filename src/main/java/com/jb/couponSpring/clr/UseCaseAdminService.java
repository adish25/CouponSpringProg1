package com.jb.couponSpring.clr;

import com.jb.couponSpring.beans.Company;
import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.repos.CustomerRepository;
import com.jb.couponSpring.services.AdminService;
import com.jb.couponSpring.services.AdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@RequiredArgsConstructor
public class UseCaseAdminService implements CommandLineRunner {

    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(">---------------- UseCaseAdminService ------------------");

        System.out.println(">---------------- UseCaseAdminService::Test Login ------------------");

        if (((AdminServiceImpl)adminService).login("admin@admin.com", "admin")) {
            System.out.println("Wellcome admin!");
        } else {
            System.out.println("Worng admin user / password !!!");
        }

        System.out.println(">---------------- UseCaseAdminService::Test add company ------------------");
        System.out.println(">----- try to add company with existing name");
        Company c1 = Company.builder()
                .email("golf.co.il")
                .password("golf")
                .name("Golf")
                .build();
        System.out.println(c1);
        try {
            adminService.addCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        c1.setName("Golg & co");
        System.out.println(">----- try to add company with existing email");

        try {
            adminService.addCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        c1.setEmail("golfnco.co.il");
        try {
            adminService.addCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        adminService.getAllCompanies().forEach(System.out::println);

        System.out.println(">---------------- UseCaseAdminService::Test update company ------------------");
        System.out.println(">----- try to update company that does not exist");
        c1.setId(999);
        try {
            adminService.updateCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(">----- try to update company name");
        c1 = adminService.getSingleCompany(4);
        c1.setName("test");
        try {
            adminService.updateCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        c1 = adminService.getSingleCompany(4);
        System.out.println(">----- test a good update");
        c1.setEmail("golfandco.co.il");
        try {
            adminService.updateCompany(c1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminService.getAllCompanies().forEach(System.out::println);


        System.out.println(">---------------- UseCaseAdminService::Test delete company ------------------");
        System.out.println("delete company -> " + adminService.getSingleCompany(3));
        try {
            adminService.deleteCompany(3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminService.getAllCompanies().forEach(System.out::println);


        System.out.println(">---------------- UseCaseAdminService::Test add customer ------------------");
        System.out.println(">----- try to add customer with existing email");

        Customer s1 = Customer.builder()
                .firstName("Ruhama")
                .lastName("Shetrit")
                .email("alon@gmail.com")
                .password("123")
                .build();
        System.out.println(s1);
        try {
            adminService.addCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(">----- try a good add customer");
        s1.setEmail("ruhama@gmail.com");
        try {
            adminService.addCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminService.getAllCustomers().forEach(System.out::println);

        System.out.println(">---------------- UseCaseAdminService::Test update customer ------------------");
        System.out.println(">----- try to update customer that does not exist");
        s1.setId(999);
        try {
            adminService.updateCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        s1 = adminService.getSingleCustomer(5);
        System.out.println(">----- test a good update");
        s1.setEmail("ruhamaUPD@gmail.com");
        try {
            adminService.updateCustomer(s1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        adminService.getAllCustomers().forEach(System.out::println);

        System.out.println(">---------------- UseCaseAdminService::Test delete customer ------------------");
        Customer s2 = Customer.builder()
                .firstName("To")
                .lastName("Delete")
                .email("todelete@gmail.com")
                .password("123")
                .build();
        try {
            adminService.addCustomer(s2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        adminService.getAllCustomers().forEach(System.out::println);
        s2 = adminService.getSingleCustomer("todelete@gmail.com","123");
        System.out.println(s2);

        try {
            adminService.deleteCustomer(s2.getId());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(">----- list of customers after delete");
        adminService.getAllCustomers().forEach(System.out::println);

    }
}
