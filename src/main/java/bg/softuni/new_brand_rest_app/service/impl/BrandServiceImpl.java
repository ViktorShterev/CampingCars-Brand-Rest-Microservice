package bg.softuni.new_brand_rest_app.service.impl;

import bg.softuni.new_brand_rest_app.entity.Brand;
import bg.softuni.new_brand_rest_app.entity.ModelId;
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

//    @Override
//    public BrandDTO addBrandModel(BrandModelAddBindingModel brandModelAddBindingModel) {
//        if (brandModelAddBindingModel != null) {
//
//            Optional<Brand> brandOptional = this.brandRepository.findByName(brandModelAddBindingModel.brand());
//
//            if (brandOptional.isPresent()) {
//                Brand brand = brandOptional.get();
//
//                return updatingBrand(brandModelAddBindingModel, brand);
//
//            } else {
//                Brand brand = new Brand()
//                        .setName(brandModelAddBindingModel.brand());
//
//                return updatingBrand(brandModelAddBindingModel, brand);
//            }
//        }
//        return null;
//    }
//
//    private BrandDTO getBrandDTO(Brand brand) {
//        Set<ModelDTO> modelDTOS = new HashSet<>();
//
//        brand.getModels()
//                .forEach(model -> {
//                    ModelDTO modelDTO = new ModelDTO(model.getId(), model.getName(), model.getCategory().getCategory().name());
//
//                    modelDTOS.add(modelDTO);
//                });
//
//        return new BrandDTO(brand.getId(), brand.getName(), modelDTOS);
//    }
//
//    private BrandDTO updatingBrand(BrandModelAddBindingModel brandModelAddBindingModel, Brand brand) {
//        Category category = this.categoryRepository.findByCategory(brandModelAddBindingModel.category())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//
//        Model model = new Model()
//                .setName(brandModelAddBindingModel.model())
//                .setCategory(category);
//
//        brand.getModels().add(model);
//        this.brandRepository.save(brand);
//
//        model.setBrand(brand);
//        this.modelRepository.save(model);
//
//        return getBrandDTO(brand);
//    }
}
