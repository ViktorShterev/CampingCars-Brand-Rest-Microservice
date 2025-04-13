package bg.softuni.new_brand_rest_app.service;


import bg.softuni.new_brand_rest_app.model.dto.BrandRestDTO;

import java.util.List;

public interface BrandService {

    List<BrandRestDTO> getAllBrands();

    void addBrandModel(BrandRestDTO brandRestDTO);

    void deleteBrand(String name);
}
