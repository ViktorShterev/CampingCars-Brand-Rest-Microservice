package bg.softuni.new_brand_rest_app.model.dto;

import java.util.Set;

public record BrandRestDTO(String name, Set<ModelRestDTO> models) {
}
