package com.main.psoos.dto;

import com.main.psoos.model.MugDesign;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MugDesignDTO {

    Integer id;

    String name;
    String type;
    boolean isDeleted;

    String path;
    public MugDesignDTO(MugDesign mug){
       // this.data = mug.getData();
        this.name = mug.getName();
        this.type = mug.getType();
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
