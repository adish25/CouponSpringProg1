package com.jb.couponSpring.repos;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.util.DBQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    boolean existsById(int couponId);

    boolean existsByCompanyIdAndTitle(int companyId, String title);

    List<Coupon> findByCompanyId(int companyId);

    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

    List<Coupon> findByCompanyIdAndPriceLessThan(int companyId, double price);

    @Modifying
    @Query(value = DBQueries.DELETE_ALL_COUPON_PURCHASES_BY_COUPON, nativeQuery = true)
    void deleteAllCouponPurchases(@Param("coupons_id") int couponId);

    @Query(value = DBQueries.QUERY_COUPONS_BY_PRICE_LIMIT, nativeQuery = true)
    List<Coupon> getCouponsByPriceLimit(@Param("company_id") int companyId);

    @Query(value = DBQueries.QUERY_CUSTOMER_COUPONS_BY_CATEGORY, nativeQuery = true)
    List<Coupon> getCustomerCouponsByCategory(@Param("customer_id") int customerId, @Param("category_id") int categoryId);

    @Query(value = DBQueries.QUERY_CUSTOMER_COUPONS_BY_PRICE_LIMIT, nativeQuery = true)
    List<Coupon> getCustomerByPriceLimit(@Param("customer_id") int customerId);

}
