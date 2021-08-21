package com.jb.couponSpring.services;

import com.jb.couponSpring.beans.Company;
import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.exceptions.CouponSystemException;

import java.util.List;

public interface AdminService {
    void addCompany(Company company) throws CouponSystemException;
    void updateCompany(Company company) throws CouponSystemException;
    void deleteCompany(int companyId) throws CouponSystemException;
    List<Company> getAllCompanies();
    Company getSingleCompany(int companyId);

    void addCustomer(Customer customer) throws CouponSystemException;
    void updateCustomer(Customer customer) throws CouponSystemException;
    void deleteCustomer(int customerId);
    List<Customer> getAllCustomers();
    Customer getSingleCustomer(int customerId);
    Customer getSingleCustomer(String email, String password);
}
