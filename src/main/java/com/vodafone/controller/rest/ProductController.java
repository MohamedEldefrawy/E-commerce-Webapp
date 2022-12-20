package com.vodafone.controller.rest;

import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.model.dto.CreateProductDto;
import com.vodafone.model.dto.UpdateProductDto;
import com.vodafone.model.response.CreateProductResponse;
import com.vodafone.model.response.DeleteProductResponse;
import com.vodafone.service.FileStorageService;
import com.vodafone.service.ProductService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private FileStorageService fileStorageService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products;

        try {
            products = this.productService.getAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (GetProductException e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product;

        try {
            product = this.productService.getById(id);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (GetProductException e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {
        List<Product> products;

        try {
            products = this.productService.getByCategory(category);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (GetProductException e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{name}")
    public ResponseEntity<List<Product>> getByName(@PathVariable String name) {
        List<Product> products;

        try {
            products = this.productService.getByCategory(name);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (GetProductException e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<CreateProductResponse> create(@RequestBody CreateProductDto createProductDto) {
        Product newProduct = new Product();
        String fileName = fileStorageService.storeFile(createProductDto.getImage());

        ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        newProduct.setName(createProductDto.getName());
        newProduct.setCategory(createProductDto.getCategory());
        newProduct.setImage(createProductDto.getImage().getOriginalFilename());
        newProduct.setPrice(createProductDto.getPrice());
        newProduct.setInStock(createProductDto.getInStock());
        newProduct.setDescription(createProductDto.getDescription());
        try {
            Long userId = this.productService.create(newProduct);
            return new ResponseEntity<>(new CreateProductResponse(userId, "Product has been created successfully."), HttpStatus.CREATED);
        } catch (CreateProductException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}")
    public ResponseEntity<CreateProductResponse> update(@PathVariable Long id, @RequestBody UpdateProductDto updateProductDto) {

        String fileName = fileStorageService.storeFile(updateProductDto.getImage());

        ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        Product updatedProduct = new Product();

        updatedProduct.setId(id);
        updatedProduct.setName(updateProductDto.getName());
        updatedProduct.setCategory(updateProductDto.getCategory());
        updatedProduct.setImage(updateProductDto.getImage().getOriginalFilename());
        updatedProduct.setPrice(updateProductDto.getPrice());
        updatedProduct.setInStock(updateProductDto.getInStock());
        updatedProduct.setDescription(updateProductDto.getDescription());
        try {
            Long userId = this.productService.update(updatedProduct);
            return new ResponseEntity<>(new CreateProductResponse(userId, "Product has been created successfully."), HttpStatus.CREATED);
        } catch (CreateProductException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DeleteProductResponse> delete(@PathVariable Long id) {
        try {
            this.productService.delete(id);
            return new ResponseEntity<>(new DeleteProductResponse("Product has been deleted successfully"), HttpStatus.OK);
        } catch (GetProductException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


}
