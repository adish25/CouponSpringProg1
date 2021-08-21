package com.jb.couponSpring.util;

public class DBQueries {

    public static final String DELETE_ALL_COUPON_PURCHASES_BY_COUPON =
            "DELETE FROM `coupon-p2`.`customers_coupons` WHERE coupons_id = :coupons_id";

    public static final String DELETE_ALL_COUPON_PURCHASES_BY_CUSTOMER =
            "DELETE FROM `coupon-p2`.`customers_coupons` WHERE customer_id = :customer_id";

    public static final String QUERY_COUPONS_BY_PRICE_LIMIT =
            "SELECT * FROM `coupon-p2`.coupons\n" +
                    "WHERE (price < \n" +
                    "(\n" +
                    "SELECT max(price) FROM `coupon-p2`.coupons\n" +
                    "WHERE (company_id = :company_id)\n" +
                    "))";

    public static final String QUERY_IS_PURCHASE_EXISTS =
            "select count(*) \n" +
                    "FROM `coupon-p2`.customers_coupons\n" +
                    "WHERE customer_id = :customer_id\n" +
                    "AND coupons_id = :coupon_id";

    public static final String QUERY_CUSTOMER_COUPONS_BY_CATEGORY = "" +
            "SELECT a.* \n" +
            "FROM `coupon-p2`.coupons as a, \n" +
            "`coupon-p2`.customers_coupons as b \n" +
            "WHERE b.customer_id = :customer_id \n" +
            "AND a.id = b.coupons_id \n" +
            "AND category_id = :category_id\n";

    public static final String QUERY_CUSTOMER_COUPONS_BY_PRICE_LIMIT = "" +
            "SELECT * FROM  `coupon-p2`.coupons\n" +
            "WHERE (price < \n" +
            "(\n" +
            "SELECT max(price)\n" +
            "FROM `coupon-p2`.coupons as a, \n" +
            "`coupon-p2`.customers_coupons as b \n" +
            "WHERE b.customer_id = :customer_id \n" +
            "AND a.id = b.coupons_id \n" +
            "))";

}
