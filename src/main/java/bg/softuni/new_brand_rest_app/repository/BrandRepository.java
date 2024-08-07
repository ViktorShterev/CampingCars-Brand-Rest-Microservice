package bg.softuni.new_brand_rest_app.repository;


import bg.softuni.new_brand_rest_app.model.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String brandName);

    void deleteByName(String brandName);
}
