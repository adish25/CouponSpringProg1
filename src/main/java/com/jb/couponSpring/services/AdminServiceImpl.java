package com.jb.couponSpring.services;

import com.jb.couponSpring.beans.Company;
import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.exceptions.CouponSystemException;
import com.jb.couponSpring.exceptions.ErrorMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminServiceImpl extends ClientService implements AdminService{

    @Override
    public void addCompany(Company company) throws CouponSystemException {
        if (this.companyRepository.existsByEmail(company.getEmail())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_EMAIL_EXIST);
        }

        if (this.companyRepository.existsByName(company.getName())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_NAME_EXIST);
        }

        this.companyRepository.save(company);
    }

    @Override
    public void updateCompany(Company company) throws CouponSystemException {

        if (!this.companyRepository.existsById(company.getId())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_DOES_NOT_EXISTS);
        }

        Company c = this.getSingleCompany(company.getId());
        if (!c.getName().equals(company.getName())) {
            throw new CouponSystemException(ErrorMsg.ADMIN_CANT_UPDATE_COMPAMY_NAME);
        }

        this.companyRepository.saveAndFlush(company);

    }

    @Override
    @Transactional
    public void deleteCompany(int companyId) throws CouponSystemException {

        if (!this.companyRepository.existsById(companyId)) {
            throw new CouponSystemException(ErrorMsg.COMPANY_DOES_NOT_EXISTS);
        }

        this.companyRepository
                .getById(companyId).getCoupons()
                .forEach(coupon -> this.couponRepository.deleteAllCouponPurchases(coupon.getId()));
        this.companyRepository
                .getById(companyId).getCoupons()
                .forEach(coupon -> this.couponRepository.deleteById(coupon.getId()));
        this.companyRepository.deleteById(companyId);
    }

    @Override
    public List<Company> getAllCompanies() {
        return this.companyRepository.findAll();
    }

    @Override
    public Company getSingleCompany(int companyId) {
        return this.companyRepository.getById(companyId);
    }

    @Override
    public void addCustomer(Customer customer) throws CouponSystemException {
        if (this.customerRepository.existsByEmail(customer.getEmail())){
            throw new CouponSystemException(ErrorMsg.ADMIN_CUSTOMER_EMAIL_EXIST);
        }

        this.customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) throws CouponSystemException {
        if (!this.customerRepository.existsById(customer.getId())) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_DOES_NOT_EXISTS);
        }

        this.customerRepository.saveAndFlush(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(int customerId) {
        this.couponRepository.deleteAllCustomerPurchases(customerId);
        this.customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Customer getSingleCustomer(int customerId) {
        return this.customerRepository.getById(customerId);
    }

    @Override
    public Customer getSingleCustomer(String email, String password) {
        return this.customerRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public boolean login(String email, String password) {
        return email.equals("admin@admin.com") && password.equals("admin");
    }
}
