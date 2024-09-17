package com.akash.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Data
//@Table(name = "Restorent_DTO")
@Embeddable
@Builder
public class RestorentDto {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long resto_id;
    private String title;
    private String name;
//    @Column(length = 1000)
//    @ElementCollection
    private List<String> images;
    private String description;

}

