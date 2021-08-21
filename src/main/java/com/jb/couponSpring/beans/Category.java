package com.jb.couponSpring.beans;

public enum Category {
    FOOD,
    ELECTRICITY,
    RESTAURANT,
    VACATION,
    FASHION,
    SPORT;

    public static Category getByIdx(int idx) {
        return Category.values()[idx];
    }
}
