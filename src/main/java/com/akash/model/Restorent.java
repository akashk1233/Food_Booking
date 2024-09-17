package com.akash.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Restorent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long restoret_id;

    private String name;
    private String description;
    private String cuisineType;

    @OneToOne
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    private String openingHours;
    private LocalDateTime registrationdate;
    private boolean open;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    @ElementCollection
    @CollectionTable(name = "restorent_images", joinColumns = @JoinColumn(name = "restorent_id"))
    @Column(name = "image_url", length = 1000)
    private List<String> images;
}
