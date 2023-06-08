package com.main.psoos.service.impl;

import com.main.psoos.model.MugDesign;
import com.main.psoos.repository.MugDesignRepository;
import com.main.psoos.service.MugDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MugDesignServiceImpl implements MugDesignService{

    @Autowired
    MugDesignRepository mugDesignRepository;

    @Override
    public void saveDesign(MugDesign design) {
        mugDesignRepository.save(design);
    }
}
