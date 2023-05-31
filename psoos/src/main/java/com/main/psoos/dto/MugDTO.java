package com.main.psoos.dto;

import com.main.psoos.model.Mug;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MugDTO {

    String orderType;
    private String mugColor;
    String jobOrder;
    private Integer noOfMug;

    MultipartFile file;
    File fileToUpload;
    String fileName;
    private String fileType;
    Integer price;

    public MugDTO(Mug mug){
        this.mugColor = mug.getMugColor();
        this.jobOrder = mug.getJobOrder();
        this.noOfMug = mug.getNoOfMug();
        this.fileType = mug.getFileType();
        this.price = mug.getPrice();
    }
}
