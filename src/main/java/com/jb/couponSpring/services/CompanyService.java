package com.jb.couponSpring.services;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Company;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.exceptions.CouponSystemException;

import java.sql.SQLException;
import java.util.List;

public interface CompanyService {
    void addCoupon(Coupon coupon) throws CouponSystemException;
    void updateCoupon(Coupon coupon) throws CouponSystemException;
    void deleteCoupon(int couponId) throws CouponSystemException;
    List<Coupon> getCompanyCoupons() throws SQLException;
    List<Coupon> getCompanyCoupons(Category category) throws SQLException;
    List<Coupon> getCouponsByCompanyPrice() throws SQLException;
    Company getCompanyByEmailPassword(String email,String password);
    Company getCompany();

}
