package com.jb.couponSpring.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String email;
    private String password;
    @OneToMany (mappedBy = "company")
    @ToString.Exclude
    @Singular
    private List<Coupon> coupons = new ArrayList<>();
}
