package com.main.psoos.dto;

import com.main.psoos.model.Shirt;
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
public class ShirtDTO {
    String orderType;
    private String shirtSize;
    String jobOrder;
    private String printType;
    private Integer noOfShirt;
    MultipartFile file;
    File fileToUpload;
    String fileName;
    Integer price;
    String fileType;
    String customizationType;

    public ShirtDTO(Shirt shirt) {
        this.shirtSize = shirt.getShirtSize();
        this.jobOrder = shirt.getJobOrder();
        this.printType = shirt.getPrintType();
        this.noOfShirt = shirt.getNoOfShirt();
        this.fileType = shirt.getFileType();
        this.price = shirt.getPrice();
    }
}
