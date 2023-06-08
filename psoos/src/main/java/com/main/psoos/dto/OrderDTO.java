package com.main.psoos.dto;

import com.main.psoos.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
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
    public OrderDTO(Order order){
        this.orderId = order.getId();
        this.customerId = order.getCustomerId();
        this.joId = order.getJoId();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.barcode = order.getBarcode();
    }
}
