package com.vodafone.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateProductResponse {
    private Long productId;
    private String message;
}
