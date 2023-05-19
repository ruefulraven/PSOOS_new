package com.main.psoos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShirtDTO {
    String orderType;
    private String shirtSize;
    private String printType;
    private Integer noOfShirt;
}
