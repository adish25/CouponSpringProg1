package com.jb.couponSpring.services;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Company;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.exceptions.CouponSystemException;
import com.jb.couponSpring.exceptions.ErrorMsg;
import lombok.Data;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CompanyServiceImpl extends ClientService implements CompanyService {

    private int companyID;

    @Override
    public boolean login(String email, String password) throws CouponSystemException {

        if (!this.companyRepository.existsByEmailAndPassword(email, password)) {
            throw new CouponSystemException(ErrorMsg.COMPANY_NOT_AUT);
        }

        this.companyID = this.getCompanyByEmailPassword(email, password).getId();

        return true;
    }


    @Override
    public void addCoupon(Coupon coupon) throws CouponSystemException {
        if (this.couponRepository.existsByCompanyIdAndTitle(coupon.getCompany().getId(), coupon.getTitle())) {
            throw new CouponSystemException(ErrorMsg.COMPANY_CANT_ADD_COUPON_WITH_SAME_TITLE);
        }

        this.couponRepository.save(coupon);
    }

    @Override
    public void updateCoupon(Coupon coupon) throws CouponSystemException {
        if (!this.couponRepository.existsById(coupon.getId())) {
            throw new CouponSystemException(ErrorMsg.COUPON_DOES_NOT_EXISTS);
        }

        Coupon c = couponRepository.getById(coupon.getId());
        if (c.getCompany().getId() != coupon.getCompany().getId()) {
            throw new CouponSystemException(ErrorMsg.COMPANY_CANT_UPDATE_COUPOS_COMPANY_ID);
        }

        this.couponRepository.saveAndFlush(coupon);
    }

    @Override
    @Transactional
    public void deleteCoupon(int couponId) throws CouponSystemException {

        Coupon c = this.couponRepository.getById(couponId);

        if (c == null) {
            throw new CouponSystemException(ErrorMsg.COUPON_DOES_NOT_EXISTS);
        }

        this.couponRepository.deleteAllCouponPurchases(couponId);
        this.couponRepository.deleteById(couponId);

    }

    @Override
    public List<Coupon> getCompanyCoupons() throws SQLException {
        return couponRepository.findByCompanyId(this.companyID);
    }

    @Override
    public List<Coupon> getCompanyCoupons(Category category) throws SQLException {
        return couponRepository.findByCompanyIdAndCategory(this.companyID,category);
    }

    @Override
    public List<Coupon> getCouponsByCompanyPrice() throws SQLException {
        //todo test
        return this.couponRepository.getCouponsByPriceLimit(this.companyID);
    }

    @Override
    public Company getCompanyByEmailPassword(String email, String password) {
        return this.companyRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Company getCompany() {
        return this.companyRepository.getById(this.companyID);
    }
}
