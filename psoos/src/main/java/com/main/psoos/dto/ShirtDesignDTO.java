package com.main.psoos.dto;

import com.main.psoos.model.ShirtDesign;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ShirtDesignDTO {

    Integer id;

    String name;
    MultipartFile data;
    String type;

    public ShirtDesignDTO(ShirtDesign shirt){
       // this.data = shirt.getData();
        this.name = shirt.getName();
        this.type = shirt.getType();
    }
}

