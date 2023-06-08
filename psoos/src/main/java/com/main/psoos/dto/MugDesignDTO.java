package com.main.psoos.dto;

import com.main.psoos.model.MugDesign;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class MugDesignDTO {

    Integer id;

    String name;
    MultipartFile data;
    String type;

    public MugDesignDTO(MugDesign mug){
       // this.data = mug.getData();
        this.name = mug.getName();
        this.type = mug.getType();
    }
}
