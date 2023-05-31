package com.main.psoos.dto;

import com.main.psoos.model.Document;
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
public class DocumentDTO {
    String orderType;
    String jobOrder;
    String paperSize;
    String printType;
    Integer noOfPages;

    MultipartFile file;
    File fileToUpload;
    String fileName;
    private String fileType;
    Integer price;


    public DocumentDTO(Document document){
        this.paperSize = document.getPaperSize();
        this.printType = document.getPrintType();
        this.noOfPages = document.getNoOfPages();
        this.fileName = document.getFileName();
        this.jobOrder = document.getJobOrder();
        this.fileType = document.getFileType();
        this.price = document.getPrice();
    }
}
