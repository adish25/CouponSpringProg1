package com.jb.couponSpring.repos;

import com.jb.couponSpring.beans.Category;
import com.jb.couponSpring.beans.Coupon;
import com.jb.couponSpring.util.DBQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Integer> {

    boolean existsById(int couponId);

    boolean existsByCompanyIdAndTitle(int companyId, String title);

    List<Coupon> findByCompanyId(int companyId);

    List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);

    List<Coupon> findByCompanyIdAndPriceLessThan(int companyId, double price);

    Coupon findByCompanyIdAndTitle(int companyId, String title);

    @Modifying
    @Query(value = DBQueries.DELETE_ALL_COUPON_PURCHASES_BY_COUPON, nativeQuery = true)
    void deleteAllCouponPurchases(@Param("coupons_id") int couponId);

    @Query(value = DBQueries.QUERY_COUPONS_BY_PRICE_LIMIT, nativeQuery = true)
    List<Coupon> getCouponsByPriceLimit(@Param("company_id") int companyId);

    @Modifying
    @Query(value = DBQueries.DELETE_ALL_COUPON_PURCHASES_BY_CUSTOMER, nativeQuery = true)
    void deleteAllCustomerPurchases(@Param("customer_id") int customerId);

    @Query(value = DBQueries.QUERY_IS_PURCHASE_EXISTS, nativeQuery = true)
    Integer isPurchaseExists(@Param("customer_id") int customerId, @Param("coupon_id") int couponId);


    @Query(value = DBQueries.QUERY_CUSTOMER_COUPONS_BY_CATEGORY, nativeQuery = true)
    List<Coupon> getCustomerCouponsByCategory(@Param("customer_id") int customerId, @Param("category_id") int categoryId);

    @Modifying
    @Query(value = DBQueries.QUERY_CUSTOMER_COUPONS_BY_PRICE_LIMIT, nativeQuery = true)
    List<Coupon> getCustomerByPriceLimit(@Param("customer_id") int customerId);

    @Modifying
    @Query(value = DBQueries.QUERY_DELETE_EXPIRED_PURCHASES, nativeQuery = true)
    void deleteExpiredPurchases(@Param("end_date") Date expirationDate);

    @Modifying
    @Query(value = DBQueries.QUERY_DELETE_EXPIRED_COUPONS, nativeQuery = true)
    void deleteExpiredCoupons(@Param("end_date") Date expirationDate);

    @Transactional
    @Modifying
    @Query(value = DBQueries.ADD_PURCHASE, nativeQuery = true)
    void insertPurchase(@Param("customer_id") int customerId, @Param("coupon_id") int couponId);

}
