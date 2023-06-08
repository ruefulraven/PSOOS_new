package com.main.psoos.repository;

import com.main.psoos.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findAllByJobOrder(String jobOrder);

    Optional<Document> findById(Integer id);
}
