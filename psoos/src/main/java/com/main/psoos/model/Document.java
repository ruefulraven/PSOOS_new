package com.main.psoos.model;


import com.main.psoos.dto.DocumentDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "document")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "paper_size")
    private String paperSize;
    @Column(name = "print_type")
    private String printType;
    @Column(name = "no_of_pages")
    private Integer noOfPages;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "job_order")
    String jobOrder;
    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(name = "price")
    private Integer price;

    public Document(DocumentDTO document){
        this.paperSize = document.getPaperSize();
        this.printType = document.getPrintType();
        this.noOfPages = document.getNoOfPages();
        this.fileName = document.getFileName();
        this.jobOrder = document.getJobOrder();
        this.fileType = document.getFileType();
    }
}
