package com.project.homework.Repository;


import com.project.homework.model.Product;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Transactional
    @Query("update Product p set p.amount = p.amount - :quantity where p.id = :id and p.amount>= :quantity")
    int findByIdForUpdate(@Param("id") Long id, @Param("quantity") Integer quantity);

    List<Product> findAllByIdIn(List<Long> id);

    @Query("SELECT p FROM Product p WHERE p.amount > :quantity")
    List<Product> findByQuantityGreaterThan(int quantity);
}