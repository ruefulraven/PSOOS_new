package com.main.psoos.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentDTO {
    String orderType;
    String paperSize;
    String printType;
    Integer noOfPages;
}
