package com.main.psoos.model;

import com.main.psoos.dto.MugDTO;
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
@Table(name = "mug")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mug_color")
    private String mugColor;

    @Column(name = "job_order")
    private String jobOrder;

    @Column(name = "no_of_mug")
    private Integer noOfMug;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

    @Column(name = "price")
    private Integer price;
    @Column(name = "mug_notes")
    String mugNotes;


    public Mug(MugDTO mug){
        this.mugColor = mug.getMugColor();
        this.jobOrder = mug.getJobOrder();
        this.noOfMug = mug.getNoOfMug();
        this.fileType = mug.getFileType();
        this.mugNotes = mug.getMugNotes();
    }
}
