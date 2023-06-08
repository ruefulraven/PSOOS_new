package com.main.psoos.service.impl;


import com.main.psoos.dto.MugDTO;
import com.main.psoos.model.Mug;
import com.main.psoos.repository.MugRepository;
import com.main.psoos.service.MugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MugServiceImpl implements MugService {

    @Autowired
    MugRepository mugRepository;

    @Override
    public void store(List<MugDTO> mugDTOS) throws IOException {
        for(MugDTO mugDTO : mugDTOS){
            String fileName = StringUtils.cleanPath(mugDTO.getFile().getOriginalFilename());
            Mug mug = new Mug(mugDTO);
            System.out.println(" Is empty? "+ mugDTO.getFile().isEmpty());
            mug.setFileName(fileName);
            //mug.setFileData(Files.readString(Paths.get(mugDTO.getFileToUpload().getPath())).getBytes());
            mug.setFileData(mugDTO.getFile().getBytes());
            mug.setFileType(mugDTO.getFile().getContentType());
            mug.setPrice(mugDTO.getPrice());
            mugRepository.save(mug);
        }
    }

    @Override
    public List<MugDTO> getMugDTOByJoId(String JoId) {
        List<MugDTO> mugDTOS = new ArrayList<MugDTO>();
        mugRepository.findAllByJobOrder(JoId).forEach(mug -> {
            MugDTO mugDTO = new MugDTO(mug);
            mugDTOS.add(mugDTO);
        });
        return mugDTOS;
    }

    @Override
    public MugDTO getMugDTOById(Integer Id) {
        return new MugDTO(mugRepository.findById(Id).get());
    }
}
