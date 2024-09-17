package com.akash.repository;

import com.akash.model.Restorent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestorentRepository extends JpaRepository<Restorent,Long> {


    @Query("SELECT r FROM Restorent r WHERE lower(r.name) LIKE lower(concat('%', :query, '%'))" +
            " OR lower(r.cuisineType) LIKE lower(concat('%', :query, '%'))")
    List<Restorent> findBySearchQuery(@Param("query") String query);


    Optional<Restorent> findByOwnerId(Long id);
}
