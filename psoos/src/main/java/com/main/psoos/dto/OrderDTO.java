package com.main.psoos.dto;

import com.main.psoos.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    Long orderId;
    List<DocumentDTO> documentDTOS;
    List<MugDTO> mugDTOS;
    List<ShirtDTO> shirtDTOS;
    Integer customerId;
    String joId;
    String status;
    Integer totalPrice;
    byte[] barcode;
    String orderStatus;
    String worker;
    String workerNotes;
    LocalDateTime finishDate;
    public OrderDTO(Order order){
        this.orderId = order.getId();
        this.customerId = order.getCustomerId();
        this.joId = order.getJoId();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.barcode = order.getBarcode();
        this.worker = order.getWorker();
        this.workerNotes = order.getWorkerNotes();
        this.orderStatus = order.getOrderStatus();
//        if(order.getFinishDate() == null || order.getFinishDate().equals("")){
//            this.finishDate = LocalDateTime.now();
//        }else{
//            this.finishDate = LocalDateTime.parse(order.getFinishDate());
//        }
    }
}
