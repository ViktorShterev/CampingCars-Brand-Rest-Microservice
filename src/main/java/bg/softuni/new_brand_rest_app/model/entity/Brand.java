package bg.softuni.new_brand_rest_app.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

   @OneToMany(mappedBy = "brand")
    private Set<ModelId> modelIds;

    public Brand() {
        this.modelIds = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public Brand setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    public Set<ModelId> getModelIds() {
        return modelIds;
    }

    public Brand setModelIds(Set<ModelId> modelIds) {
        this.modelIds = modelIds;
        return this;
    }
}
