package com.vodafone.controller.rest;

import com.vodafone.exception.FileStorageException;
import com.vodafone.exception.product.CreateProductException;
import com.vodafone.exception.product.GetProductException;
import com.vodafone.model.Product;
import com.vodafone.model.response.DeleteProductResponse;
import com.vodafone.model.response.ProductResponse;
import com.vodafone.service.ProductService;
import com.vodafone.service.file.FileStorageService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private FileStorageService fileStorageService;
    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<Product>> getAll(@RequestParam Optional<String> name, @RequestParam Optional<String> category) {
        List<Product> products;

        if (name.isPresent() && category.isPresent()) {
            try {
                products = this.productService.getByNameAndCategory(name.get(), category.get());
                return new ResponseEntity<>(products, HttpStatus.OK);
            } catch (GetProductException | IllegalArgumentException e) {
                logger.warn(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else if (name.isPresent()) {
            try {
                products = this.productService.getByName(name.get());
                return new ResponseEntity<>(products, HttpStatus.OK);
            } catch (GetProductException | IllegalArgumentException e) {
                logger.warn(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else if (category.isPresent()) {
            try {
                products = this.productService.getByCategory(category.get());
                return new ResponseEntity<>(products, HttpStatus.OK);
            } catch (GetProductException | IllegalArgumentException e) {
                logger.warn(e.getMessage());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } else
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

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestParam(name = "image", required = false)
                                                  MultipartFile image,
                                                  @RequestParam(name = "name") String name,
                                                  @RequestParam(name = "description") String description,
                                                  @RequestParam(name = "category") String category,
                                                  @RequestParam(name = "price") Double price,
                                                  @RequestParam(name = "inStock") int inStock) {
        Product newProduct = new Product();
        try {
            fileStorageService.storeFile(image);
            newProduct.setImage(image.getOriginalFilename());
        } catch (FileStorageException e) {
            logger.warn(e.getMessage());
        }


        newProduct.setName(name);
        newProduct.setCategory(category);
        newProduct.setPrice(price);
        newProduct.setInStock(inStock);
        newProduct.setDescription(description);
        try {
            Long userId = this.productService.create(newProduct);
            return new ResponseEntity<>(new ProductResponse(userId, "Product has been created successfully."), HttpStatus.CREATED);
        } catch (CreateProductException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,
                                                  @RequestParam(name = "image", required = false)
                                                  MultipartFile image,
                                                  @RequestParam(name = "name") String name,
                                                  @RequestParam(name = "description") String description,
                                                  @RequestParam(name = "category") String category,
                                                  @RequestParam(name = "price") Double price,
                                                  @RequestParam(name = "inStock") int inStock) {

        Product updatedProduct = new Product();

        try {
            fileStorageService.storeFile(image);
            updatedProduct.setImage(image.getOriginalFilename());

        } catch (FileStorageException e) {
            logger.warn(e.getMessage());
        }


        updatedProduct.setId(id);
        updatedProduct.setName(name);
        updatedProduct.setCategory(category);
        updatedProduct.setPrice(price);
        updatedProduct.setInStock(inStock);
        updatedProduct.setDescription(description);
        try {
            Long userId = this.productService.update(updatedProduct);
            return new ResponseEntity<>(new ProductResponse(userId, "Product has been updated successfully."), HttpStatus.OK);
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
