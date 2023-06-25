package com.main.psoos.service.impl;

import com.main.psoos.dto.OrderDTO;
import com.main.psoos.model.Order;
import com.main.psoos.repository.OrderRepository;
import com.main.psoos.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

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

    @Transactional
    @Override
    public byte[] getImage(String joId) {
       Order dbImage = orderRepository.findByJoId(joId);
        OrderDTO dto = new OrderDTO(dbImage);
        //return decompressImage(dto.getBarcode());
        return dto.getBarcode();
    }

    @Override
    public List<Order> getAllOrdersByDate(Date startDate, Date endDate) {
        return orderRepository.findAllByCreationDateBetween(startDate, endDate);
    }

    public  byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }
}
