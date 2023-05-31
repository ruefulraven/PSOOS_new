package com.main.psoos.repository;

import com.main.psoos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(Integer customerId);

    Order findByJoId(String joId);

}
