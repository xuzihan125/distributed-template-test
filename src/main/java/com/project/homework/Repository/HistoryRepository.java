package com.project.homework.Repository;

import com.project.homework.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, String> {
    List<History> findByUsername(String username);

    @Query("SELECT h FROM History h WHERE h.productId = :productId AND h.username = :username AND h.id = :id")
    List<History> findByProductIdAndUsernameAndId(@Param("productId") Long productId, @Param("username") String username, @Param("id") String id);
}
