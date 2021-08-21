package com.jb.couponSpring.services;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.exceptions.CouponSystemException;
import com.jb.couponSpring.exceptions.ErrorMsg;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CustomerServiceImpl extends ClientService implements CustomerService {

    private int customerID;

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if (!this.customerRepository.existsByEmailAndPassword(email, password)) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_DOES_NOT_EXISTS);
        }

        this.customerID = this.getCustomerByEmailPassword(email, password).getId();

        return true;
    }

    @Override
    @Transactional
    public void purchaseCoupon(Coupon coupon) throws CouponSystemException {

        if (couponRepository.isPurchaseExists(this.customerID, coupon.getId()) > 0) {
            throw new CouponSystemException(ErrorMsg.CUSTOMER_ALREADY_PURCHESED_COUPON);
        }


        if (coupon.getAmount() == 0) {
            throw new CouponSystemException(ErrorMsg.NO_COUPONS_LEFT_TO_PURCHES);
        }

        if (coupon.getEndDate().compareTo(Date.valueOf(LocalDate.now())) < 0) {
            throw new CouponSystemException(ErrorMsg.EXPIRED_COUPON);
        }

        couponRepository.insertPurchase(this.customerID, coupon.getId());

        coupon.setAmount(coupon.getAmount()-1);
        couponRepository.saveAndFlush(coupon);

    }

    @Override
    public List<Coupon> getCustomerCoupons() {
        return this.getCustomerDetails().getCoupons();
    }

    @Override
    public List<Coupon> getCustomerCoupons(Category category) {
        return couponRepository.getCustomerCouponsByCategory(this.customerID,category.ordinal());
    }

    @Override
    public List<Coupon> getCustomerCouponsByPrice() {
        return couponRepository.getCustomerByPriceLimit(this.customerID);
    }

    @Override
    public Customer getCustomerDetails() {
        return customerRepository.getById(customerID);
    }

    public Customer getCustomerByEmailPassword(String email, String password) {
        return customerRepository.findByEmailAndPassword(email, password);
    }
}
