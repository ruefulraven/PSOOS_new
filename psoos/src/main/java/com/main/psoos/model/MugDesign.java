package com.main.psoos.model;

import com.main.psoos.dto.MugDesignDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Getter
@Setter
@Entity( name = "mug_design")
@NoArgsConstructor
public class MugDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "name")
    String name;
    @Column(name = "data")
    @Lob
    byte[] data;
    @Column(name = "type")
    String type;

    public MugDesign(MugDesignDTO mug) throws IOException {
        this.data = mug.getData().getBytes();
        this.name = mug.getName();
        this.type = mug.getType();
    }
}
