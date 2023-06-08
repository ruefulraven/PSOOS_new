package com.main.psoos.repository;

import com.main.psoos.model.ShirtDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShirtDesignRepository extends JpaRepository<ShirtDesign, Integer> {

    ShirtDesign findByName(String name);
}
