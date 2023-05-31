package com.main.psoos.service.impl;

import com.main.psoos.dto.DocumentDTO;
import com.main.psoos.model.Document;
import com.main.psoos.repository.DocumentRepository;
import com.main.psoos.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    DocumentRepository documentRepository;


    @Override
    public void store(List<DocumentDTO> documentDTO) throws IOException {
        for(DocumentDTO docDTO : documentDTO){
            String fileName = StringUtils.cleanPath(docDTO.getFile().getOriginalFilename());
            Document document = new Document(docDTO);
            System.out.println(" Is empty? "+ docDTO.getFile().isEmpty());
            document.setFileName(fileName);
            document.setFileData(Files.readString(Paths.get(docDTO.getFileToUpload().getPath())).getBytes());
            document.setFileType(docDTO.getFile().getContentType());
            document.setPrice(docDTO.getPrice());
            documentRepository.save(document);
        }
    }

    @Override
    public List<DocumentDTO> getDocumentDTOByJoId(String JobOrder) {
        List<DocumentDTO> documentDTOS = new ArrayList<DocumentDTO>();
        documentRepository.findAll().forEach(document -> {
            DocumentDTO documentDTO = new DocumentDTO(document);
            documentDTOS.add(documentDTO);
        });
        return documentDTOS;
    }


}
