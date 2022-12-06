package com.vodafone.model.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateProductDto {
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    private String category;
    @NotNull
    @Min(value = 0)
    private Double price;
    @Min(value = 1)
    private Integer inStock;

    private CommonsMultipartFile image;
}
