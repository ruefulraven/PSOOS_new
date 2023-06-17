package com.main.psoos.service;

import com.main.psoos.model.MugDesign;

import java.util.List;

public interface MugDesignService {

    void saveDesign(MugDesign design);

    List<MugDesign> getAllMugDesigns();

    void deleteDesignById(Integer id);
}
