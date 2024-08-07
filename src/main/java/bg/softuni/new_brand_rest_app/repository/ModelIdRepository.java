package bg.softuni.new_brand_rest_app.repository;

import bg.softuni.new_brand_rest_app.entity.ModelId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelIdRepository extends JpaRepository<ModelId, Long> {

    void deleteAllByBrandId(Long brandId);
}
