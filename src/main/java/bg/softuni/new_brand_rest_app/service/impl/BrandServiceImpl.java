package bg.softuni.new_brand_rest_app.service.impl;

import bg.softuni.new_brand_rest_app.model.entity.Brand;
import bg.softuni.new_brand_rest_app.model.entity.ModelId;
import bg.softuni.new_brand_rest_app.model.dto.BrandRestDTO;
import bg.softuni.new_brand_rest_app.model.dto.ModelRestDTO;
import bg.softuni.new_brand_rest_app.repository.BrandRepository;
import bg.softuni.new_brand_rest_app.repository.ModelIdRepository;
import bg.softuni.new_brand_rest_app.service.BrandService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final ModelIdRepository modelIdRepository;

    public BrandServiceImpl(BrandRepository brandRepository, ModelIdRepository modelIdRepository) {
        this.brandRepository = brandRepository;
        this.modelIdRepository = modelIdRepository;
    }


    @Override
    public List<BrandRestDTO> getAllBrands() {
        List<Brand> all = this.brandRepository.findAll();

        List<BrandRestDTO> brandRestDTOS = new ArrayList<>();

        for (Brand brand : all) {

            Set<ModelRestDTO> modelRestDTOS = new HashSet<>();

            brand.getModelIds()
                    .forEach(
                            modelId -> {
                                ModelRestDTO modelRestDTO = new ModelRestDTO(modelId.getValue());
                                modelRestDTOS.add(modelRestDTO);
                            }
                    );

            BrandRestDTO brandRestDTO = new BrandRestDTO(brand.getName(), modelRestDTOS);
            brandRestDTOS.add(brandRestDTO);
        }

        return brandRestDTOS;
    }

    @Override
    @Transactional
    public void addBrandModel(BrandRestDTO brandRestDTO) {

        Optional<Brand> optionalBrand = this.brandRepository.findByName(brandRestDTO.name());

        if (optionalBrand.isPresent()) {
            Brand brand = optionalBrand.get();

            updatingBrandModel(brandRestDTO, brand);

        } else {
            Brand brand = new Brand();
            brand.setName(brandRestDTO.name());

            updatingBrandModel(brandRestDTO, brand);
        }
    }

    @Override
    @Transactional
    public void deleteBrand(String name) {

        Brand brand = this.brandRepository.findByName(name).get();

        this.modelIdRepository.deleteAllByBrandId(brand.getId());

        this.brandRepository.deleteByName(name);
    }

    private void updatingBrandModel(BrandRestDTO brandRestDTO, Brand brand) {
        ModelRestDTO modelRestDTO = brandRestDTO.models().stream().findFirst().get();

        ModelId modelId = new ModelId();
        modelId.setBrand(brand);
        modelId.setValue(modelRestDTO.modelId());

        this.modelIdRepository.save(modelId);

        Set<ModelId> modelIds = brand.getModelIds();
        modelIds.add(modelId);

        brand.setModelIds(modelIds);
        this.brandRepository.save(brand);
    }
}
