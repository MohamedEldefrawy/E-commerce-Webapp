package com.vodafone.model.dto;

import lombok.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateProductDto {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String category;
    @NotNull
    @Min(value = 0)
    private double price;
    @NotNull
    @Min(value = 1)
    private int inStock;

    private CommonsMultipartFile image;

}
