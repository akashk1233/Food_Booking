package com.akash.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodId;

    private String name;
    private String description;
    private Long price;

    @ManyToOne
    private Category foodCategory;

    @ElementCollection
    @CollectionTable(name = "food_images", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "image_url", length = 1000)
    private List<String> images;

    private boolean isAvailable;

    @ManyToOne
    private Restorent restorent;

    private boolean isSeasonal;

    @ManyToMany
    @JoinTable(
            name = "food_ingredients",
            joinColumns = @JoinColumn(name = "food_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<IngredientsItem> ingredientsItems;
}
