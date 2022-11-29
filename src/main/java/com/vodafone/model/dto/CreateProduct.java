package com.vodafone.model.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateProduct {
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String image;
    @NotNull
    @NotBlank
    private String category;
    @NotBlank
    @Min(value = 1)
    private double price;
}
