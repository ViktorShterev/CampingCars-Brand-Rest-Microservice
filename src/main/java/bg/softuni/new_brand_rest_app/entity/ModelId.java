package bg.softuni.new_brand_rest_app.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "model_ids")
public class ModelId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long value;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;

    public Long getId() {
        return id;
    }

    public ModelId setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getValue() {
        return value;
    }

    public ModelId setValue(Long value) {
        this.value = value;
        return this;
    }

    public Brand getBrand() {
        return brand;
    }

    public ModelId setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }
}
