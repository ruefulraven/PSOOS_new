package com.main.psoos.service.impl;

import com.main.psoos.model.ShirtDesign;
import com.main.psoos.repository.ShirtDesignRepository;
import com.main.psoos.service.ShirtDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShirtDesignServiceImpl implements ShirtDesignService {


    @Autowired
    ShirtDesignRepository shirtDesignRepository;

    @Override
    public void saveDesign(ShirtDesign shirt) {
        shirtDesignRepository.save(shirt);
    }

    @Override
    public ShirtDesign getDesign(String designName) {
        return shirtDesignRepository.findByName(designName);
    }

    @Override
    public ShirtDesign getDesignById(Integer id) {
        return shirtDesignRepository.findById(id).orElse(null);
    }

    @Override
    public List<ShirtDesign> getAllDesign() {
        return shirtDesignRepository.findAll().stream().
                filter(design -> !design.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public void deleteDesignById(Integer id) {
        shirtDesignRepository.deleteDesignById(id);
    }
}
