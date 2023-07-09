package com.main.psoos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ToBeSubmitOrders {

    String orderType; //Tshirt, Mugs, Document
    Integer noOfType;
    String Status = "PENDING";
}
