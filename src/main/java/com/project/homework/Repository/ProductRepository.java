package com.project.homework.Repository;


import com.project.homework.model.Product;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Product findByIdForUpdate(@Param("id") Long id);

    List<Product> findAllByIdIn(List<Long> id);

    @Query("SELECT p FROM Product p WHERE p.amount > :quantity")
    List<Product> findByQuantityGreaterThan(int quantity);
}