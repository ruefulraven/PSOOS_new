package com.main.psoos.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ReportsDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFrom;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTo;

    private String importType;
}
