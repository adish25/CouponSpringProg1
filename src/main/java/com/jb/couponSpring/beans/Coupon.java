package com.jb.couponSpring.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "coupons")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne    // each company can have many unique coupons
    @ToString.Exclude
    private Company company;
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
}
