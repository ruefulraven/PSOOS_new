package com.main.psoos.repository;

import com.main.psoos.model.Shirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShirtRepository extends JpaRepository<Shirt, Integer> {

    List<Shirt> findAllByJobOrder(String jobOrder);
}
