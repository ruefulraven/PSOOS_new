package com.main.psoos.repository;

import com.main.psoos.model.Mug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MugRepository extends JpaRepository<Mug, Integer> {

    List<Mug> findAllByJobOrder(String jobOrder);

    Optional<Mug> findById(Integer id);
}
