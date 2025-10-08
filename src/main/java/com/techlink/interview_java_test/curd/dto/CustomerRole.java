package com.techlink.interview_java_test.curd.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private RoleType roleType;

    public enum RoleType {
        ADMIN, RETAIL, WHOLESALER, BROKER
    }
}