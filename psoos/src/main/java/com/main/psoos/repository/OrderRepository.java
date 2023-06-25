package com.main.psoos.repository;

import com.main.psoos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(Integer customerId);

    Order findByJoId(String joId);

    @Query(value = "select * from orders where creation_date between :startDate AND :endDate", nativeQuery = true)
    List<Order> findAllByCreationDateBetween(@Param("startDate") Date startDate, @Param("endDate")Date endDate);

}
