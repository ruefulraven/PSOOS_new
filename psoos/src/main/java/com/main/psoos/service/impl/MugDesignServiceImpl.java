package com.main.psoos.service.impl;

import com.main.psoos.model.MugDesign;
import com.main.psoos.repository.MugDesignRepository;
import com.main.psoos.service.MugDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MugDesignServiceImpl implements MugDesignService{

    @Autowired
    MugDesignRepository mugDesignRepository;

    @Override
    public void saveDesign(MugDesign design) {
        mugDesignRepository.save(design);
    }

    @Override
    public List<MugDesign> getAllMugDesigns() {
        return mugDesignRepository.findAll().
                stream().
                filter(design -> !design.isDeleted()).collect(Collectors.toList());
    }

    @Override
    public void deleteDesignById(Integer id) {
        mugDesignRepository.deleteDesignById(id);
    }
}
