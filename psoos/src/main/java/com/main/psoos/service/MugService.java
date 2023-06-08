package com.main.psoos.service;


import com.main.psoos.dto.MugDTO;

import java.io.IOException;
import java.util.List;

public interface MugService {
    public void store(List<MugDTO> mugDTOS) throws IOException;

    public List<MugDTO> getMugDTOByJoId(String JoId);

    public MugDTO getMugDTOById(Integer Id);
}
