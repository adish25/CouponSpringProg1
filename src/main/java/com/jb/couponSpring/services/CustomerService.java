package com.jb.couponSpring.services;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.exceptions.CouponSystemException;

import java.util.List;

public interface CustomerService {
    void purchaseCoupon(Coupon coupon) throws CouponSystemException;
    List<Coupon> getCustomerCoupons();
    List<Coupon> getCustomerCoupons(Category category);
    List<Coupon> getCustomerCouponsByPrice();
    Customer getCustomerDetails();
    Customer getCustomerByEmailPassword(String email, String password);
}
