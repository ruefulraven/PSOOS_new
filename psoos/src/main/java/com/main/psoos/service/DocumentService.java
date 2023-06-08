package com.main.psoos.service;

import com.main.psoos.dto.DocumentDTO;

import java.io.IOException;
import java.util.List;

public interface DocumentService {

    public void store(List<DocumentDTO> documentDTO) throws IOException;

    public List<DocumentDTO> getDocumentDTOByJoId(String JobOrder);

    public DocumentDTO getDocumentDTOById(Integer Id);
}
