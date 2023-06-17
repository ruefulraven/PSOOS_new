package com.main.psoos.service;

import com.main.psoos.model.ShirtDesign;

import java.util.List;

public interface ShirtDesignService {

    void saveDesign(ShirtDesign shirt);

    ShirtDesign getDesign(String designName);

    ShirtDesign getDesignById(Integer id);

    List<ShirtDesign> getAllDesign();

    void deleteDesignById(Integer id);
}
