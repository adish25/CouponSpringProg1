package com.jb.couponSpring.exceptions;

public enum ErrorMsg {
    COMPANY_NAME_EXIST("cannot add company with exiting name"),
    COMPANY_EMAIL_EXIST("cannot add company with exiting email"),
    COMPANY_NOT_AUT("Company is not authorized, check your email and password"),
    ADMIN_CANT_UPDATE_COMPAMY_NAME("Admin cannot update company name"),
    ADMIN_CANT_UPDATE_COMPAMY_ID("Admin cannot update company id"),
    ADMIN_CUSTOMER_EMAIL_EXIST("Customer with the same email already exists"),
    CUSTOMER_DOES_NOT_EXISTS("Customer does not exists"),
    COMPANY_DOES_NOT_EXISTS("Company does not exists"),
    ADMIN_CANT_UPDATE_CUSTOMER_ID("Admin cannot update customer id"),
    COMPANY_CANT_ADD_COUPON_WITH_SAME_TITLE("Company can't add coupon of the same company with the same title"),
    COUPON_DOES_NOT_EXISTS("Coupon does not exists"),
    COMPANY_CANT_UPDATE_COUPOS_COMPANY_ID("Company can't update a coupon's company id"),
    CUSTOMER_ALREADY_PURCHESED_COUPON("You can't purchase this coupon twice!"),
    NO_COUPONS_LEFT_TO_PURCHES("This coupon is not availale any more for purchase"),
    EXPIRED_COUPON("You can't purchase this coupon, it is expired"),
    INVALID_CLIENT_CREDENTIALS("Invalid client credentials");

    private String msg;

    ErrorMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
