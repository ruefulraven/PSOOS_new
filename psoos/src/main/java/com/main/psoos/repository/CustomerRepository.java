package com.main.psoos.repository;
import com.main.psoos.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByCustomerName(String name);

    Customer findByCustomerId(Integer id);

}