package com.main.psoos.repository;

import com.main.psoos.model.ShirtDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ShirtDesignRepository extends JpaRepository<ShirtDesign, Integer> {

    ShirtDesign findByName(String name);


    @Modifying
    @Transactional
    @Query(value = "update shirt_design sd set sd.is_deleted = true where sd.id = :id", nativeQuery = true)
    public void deleteDesignById(@Param("id") Integer id);
}
