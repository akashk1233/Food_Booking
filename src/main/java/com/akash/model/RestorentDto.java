package com.akash.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

import java.util.List;

@Entity
@Data
@Table(name = "Restorent_DTO")
@Embeddable
public class RestorentDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resto_id;
    private String title;
    @Column(length = 1000)
    private List<String> images;
    private String description;

}
