package com.main.psoos.service;

import com.main.psoos.model.Order;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvService {

    @Autowired
    private CustomerService customerService;


    public void printOrders(List<Order> order, Writer writer){
        try{
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            printer.printRecord("Job ID",
                    "Customer Name",
                    "Total Price",
                    "Job Order Created",
                    "Status",
                    "Worker");
            for(Order tempOrder : order){
                String customerName = customerService
                        .getCustomerById(tempOrder.getCustomerId())
                        .getCustomerName();

                printer.printRecord(tempOrder.getJoId(),
                        customerName,
                        tempOrder.getTotalPrice(),
                        tempOrder.getCreationDate(),
                        tempOrder.getStatus(),
                        tempOrder.getWorker());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
