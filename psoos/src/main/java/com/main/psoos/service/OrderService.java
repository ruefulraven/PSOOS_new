package com.main.psoos.service;

import com.main.psoos.model.Order;
import java.util.Date;
import java.util.List;

public interface OrderService {
    Integer countOrder();

    List<Order> getOrdersById(Integer customerId);
    List<Order> getAllOrders();

    void saveOrders(Order orders);

    Order findByJobId(String jobId);

    byte[] getImage(String joId);

    List<Order> getAllOrdersByDate(Date startDate, Date endDate);
}
