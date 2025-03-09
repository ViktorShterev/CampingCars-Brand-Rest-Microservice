package bg.softuni.new_brand_rest_app.web;

import bg.softuni.new_brand_rest_app.model.dto.BrandRestDTO;
import bg.softuni.new_brand_rest_app.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("http://brands-microservice:8081/brands")
@Tag(
        name = "Brands",
        description = "The controller responsible for brand management."
)
public class BrandController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All Brands",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = BrandRestDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(responseCode = "404", description = "If brands were not found",
                            content = {
                                    @Content(
                                            mediaType = "application/json"
                                    )
                            }
                    )
            }
    )
    @GetMapping("/all")
    public ResponseEntity<List<BrandRestDTO>> getAllBrands() {
        LOGGER.info("getAllBrands");

        return ResponseEntity
                .ok(this.brandService.getAllBrands());
    }

    @Operation(
            security = @SecurityRequirement(
                    name = "bearer-token"
            )
    )
    @PostMapping("/add")
    public ResponseEntity<Void> addBrand(
            @RequestBody BrandRestDTO brandRestDTO
    ) {
        LOGGER.info("add Brand");

        this.brandService.addBrandModel(brandRestDTO);

        return ResponseEntity.noContent().build();
    }

    @Operation(
            security = @SecurityRequirement(
                    name = "bearer-token"
            )
    )
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String name) {
        LOGGER.info("delete Brand");

        this.brandService.deleteBrand(name);

        return ResponseEntity.noContent().build();
    }


//    @Operation(
//            security = @SecurityRequirement(
//                    name = "bearer-token"
//            )
//    )
//    @PostMapping("/add")
//    public ResponseEntity<BrandDTO> addBrandModel(
//            @RequestBody BrandModelAddBindingModel brandModelAddBindingModel
//    ) {
//        LOGGER.info("Going to create a brand/model {}", brandModelAddBindingModel);
//
//        BrandDTO brandDTO = this.brandService.addBrandModel(brandModelAddBindingModel);
//
//        return ResponseEntity.
//                created(
//                        ServletUriComponentsBuilder
//                                .fromCurrentRequest()
//                                .path("/{id}")
//                                .buildAndExpand(brandDTO.id())
//                                .toUri()
//                ).body(brandDTO);
//    }


//    @Operation(
//            security = @SecurityRequirement(
//                    name = "bearer-token"
//            )
//    )
//    @DeleteMapping("/{id}")
//    public ResponseEntity<OfferDTO> deleteById(@PathVariable("id") Long id,
//                                               @AuthenticationPrincipal UserDetails userDetails) {
//        offerService.deleteOffer(userDetails, id);
//        return ResponseEntity
//                .noContent()
//                .build();
//    }
}
