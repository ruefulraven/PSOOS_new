package com.main.psoos.service.impl;

import com.main.psoos.dto.ShirtDTO;
import com.main.psoos.model.Shirt;
import com.main.psoos.repository.ShirtRepository;
import com.main.psoos.service.ShirtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShirtServiceImpl implements ShirtService {

    @Autowired
    ShirtRepository shirtRepository;

    @Override
    public void store(List<ShirtDTO> shirtDTO) throws IOException {
        for(ShirtDTO shirtTO : shirtDTO){
            String fileName = StringUtils.cleanPath(shirtTO.getFile().getOriginalFilename());
            Shirt shirt = new Shirt(shirtTO);
            System.out.println(" Is empty? "+ shirtTO.getFile().isEmpty());
            shirt.setFileName(fileName);
            shirt.setFileData(shirtTO.getFile().getBytes());
            shirt.setFileType(shirtTO.getFile().getContentType());
            shirt.setPrice(shirtTO.getPrice());
            shirtRepository.save(shirt);
        }
    }

    @Override
    public List<ShirtDTO> getShirtDTOByJoId(String JoId) {
        List<ShirtDTO> shirtDTOS = new ArrayList<ShirtDTO>();
        shirtRepository.findAllByJobOrder(JoId).forEach(shirt -> {
            ShirtDTO shirtDTO = new ShirtDTO(shirt);
            shirtDTOS.add(shirtDTO);
        });
        return shirtDTOS;
    }

    @Override
    public ShirtDTO getShirtDTOById(Integer Id) {
        return new ShirtDTO(shirtRepository.findById(Id).get());
    }
}
