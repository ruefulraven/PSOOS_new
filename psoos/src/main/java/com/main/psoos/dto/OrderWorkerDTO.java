package com.main.psoos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderWorkerDTO {

    String worker;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateToFinish;
    String joId;
}
