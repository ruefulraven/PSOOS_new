package com.main.psoos.service.impl;

import com.main.psoos.model.ShirtDesign;
import com.main.psoos.repository.ShirtDesignRepository;
import com.main.psoos.service.ShirtDesignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShirtDesignServiceImpl implements ShirtDesignService {


    @Autowired
    ShirtDesignRepository shirtDesignRepository;

    @Override
    public void saveDesign(ShirtDesign shirt) {
        shirtDesignRepository.save(shirt);
    }
}
