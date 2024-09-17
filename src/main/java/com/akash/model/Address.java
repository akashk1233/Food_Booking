package com.akash.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long address_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users customer;

    private String city;
    private String state;
    private Long postalCode;
    private String country;
}
