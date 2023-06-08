package com.main.psoos.repository;

import com.main.psoos.model.MugDesign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MugDesignRepository extends JpaRepository<MugDesign, Integer> {

    MugDesign findByName(String name);

}
