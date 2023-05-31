package com.main.psoos.service.impl;

import com.main.psoos.model.Order;
import com.main.psoos.repository.OrderRepository;
import com.main.psoos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Integer countOrder() {
        return Math.toIntExact(orderRepository.count());
    }

    @Override
    public List<Order> getOrdersById(Integer customerId) {
        return orderRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void saveOrders(Order order) {
       orderRepository.save(order);

    }

    @Override
    public Order findByJobId(String jobId) {
        return orderRepository.findByJoId(jobId);
    }
}
