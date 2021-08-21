package com.jb.couponSpring.repos;

import com.jb.couponSpring.beans.Customer;
import com.jb.couponSpring.util.DBQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    boolean existsByEmail(String email);

    boolean existsByEmailAndPassword(String email, String password);

    Customer findByEmailAndPassword(String email, String password);

    @Modifying
    @Query(value = DBQueries.DELETE_ALL_COUPON_PURCHASES_BY_CUSTOMER, nativeQuery = true)
    void deleteAllCustomerPurchases(@Param("customer_id") int customerId);

    @Query(value = DBQueries.QUERY_IS_PURCHASE_EXISTS, nativeQuery = true)
    Integer isPurchaseExists(@Param("customer_id") int customerId, @Param("coupon_id") int couponId);


}
