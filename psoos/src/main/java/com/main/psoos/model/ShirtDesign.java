package com.main.psoos.model;

import com.main.psoos.dto.ShirtDesignDTO;
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
@Entity( name = "shirt_design")
@NoArgsConstructor
public class ShirtDesign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Column(name = "name")
    String name;
    @Column(name = "type")
    String type;

    @Column(name = "is_deleted")
    boolean isDeleted;

    public ShirtDesign(ShirtDesignDTO shirt) throws IOException {
        this.id = shirt.getId();
        this.name = shirt.getName();
        this.type = shirt.getType();
        this.isDeleted = shirt.isDeleted();

    }
}
