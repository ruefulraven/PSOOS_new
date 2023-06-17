package com.main.psoos.repository;

import com.main.psoos.model.MugDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MugDesignRepository extends JpaRepository<MugDesign, Integer> {

    MugDesign findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "update mug_design md set md.is_deleted = true where md.id = :id", nativeQuery = true)
    public void deleteDesignById(@Param("id") Integer id);
}
