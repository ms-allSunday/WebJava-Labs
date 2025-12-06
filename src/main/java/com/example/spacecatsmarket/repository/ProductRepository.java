package com.example.spacecatsmarket.repository;

import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.repository.projection.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query("SELECT new com.example.spacecatsmarket.repository.projection.ProductProjection(p.name, CAST(p.price AS double)) " +
            "FROM ProductEntity p " +
            "WHERE p.price > :minPrice " +
            "ORDER BY p.price DESC")
    List<ProductProjection> findProductsByPriceGreaterThan(@Param("minPrice") double minPrice);
}
