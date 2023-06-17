package com.main.psoos.dto;

import com.main.psoos.model.ShirtDesign;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ShirtDesignDTO {

    Integer id;

    String name;
    String type;
    String path;
    boolean isDeleted;

    public ShirtDesignDTO(ShirtDesign shirt){

        this.id = shirt.getId();
        this.name = shirt.getName();
        this.type = shirt.getType();
        this.isDeleted = shirt.isDeleted();
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}

