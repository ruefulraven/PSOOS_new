package com.main.psoos.service;

import com.main.psoos.dto.ShirtDTO;

import java.io.IOException;
import java.util.List;

public interface ShirtService {
    public void store(List<ShirtDTO> shirtDTO) throws IOException;

    public List<ShirtDTO> getShirtDTOByJoId(String JoId);
}
