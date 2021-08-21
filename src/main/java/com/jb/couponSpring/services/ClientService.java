package com.jb.couponSpring.services;

import com.jb.couponSpring.exceptions.CouponSystemException;
import com.jb.couponSpring.repos.CompanyRepository;
import com.jb.couponSpring.repos.CouponRepository;
import com.jb.couponSpring.repos.CustomerRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public abstract class ClientService {

    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CouponRepository couponRepository;

    public abstract boolean login(String email, String password) throws CouponSystemException;
}
